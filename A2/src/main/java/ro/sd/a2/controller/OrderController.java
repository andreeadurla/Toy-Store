package ro.sd.a2.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ro.sd.a2.dto.*;
import ro.sd.a2.dtoValidator.CreditCardDTOValidator;
import ro.sd.a2.dtoValidator.PlaceOrderDTOValidator;
import ro.sd.a2.exception.InsufficientStockException;
import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.message.WrongMessage;
import ro.sd.a2.service.*;
import ro.sd.a2.service.document.invoice.InvoiceService;
import ro.sd.a2.service.document.invoice.PdfInvoice;
import ro.sd.a2.service.document.invoice.TxtInvoice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Controller
@RequiredArgsConstructor
public class OrderController {

    private static final Logger logger = Logger.getLogger(OrderController.class.getName());

    @Autowired
    private DeliveryAddressService deliveryAddressService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShoppingCartService shopping_cartService;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private RabbitMQSender rabbitMQOrderSender;

    @Autowired
    private PdfInvoice pdfInvoice;

    @Autowired
    private TxtInvoice txtInvoice;

    @Autowired
    private UserAuthentication userAuthentication;

    private PlaceOrderDTO currentOrder;

    /**
     *
     * @return a form for adding a new order. This form contains delivery addresses of authenticated
     * user and total price of order.
     */
    @GetMapping("/placeOrder")
    public ModelAndView placeOrder() {
        logger.log(Level.INFO, "/placeOrder page accessed");

        ModelAndView mav = new ModelAndView();

        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        List<DeliveryAddressDTO> delivery_addressDTOs = deliveryAddressService.findByUserId(userDTO.getId());
        List<ShoppingCartDetailsDTO> shoppingCartDetailsDTOs = shopping_cartService.findByUserId(userDTO.getId());
        float price = ShoppingCart.computeTotalPrice(shoppingCartDetailsDTOs);

        mav.addObject("delivery_addresses", delivery_addressDTOs);
        mav.addObject("user", userDTO);
        mav.addObject("price", price);

        mav.setViewName("order");
        return mav;
    }

    /**
     * Add a new order
     * @param orderDTO data corresponding to the new order
     * @return home page if order was successfully created and client will pay with cash. If client wants to
     * pay with card, this method returns a page where he completes the requested data. If order wasn't created
     * this method returns the place order page containing the corresponding error message.
     */
    @PostMapping("/addOrder")
    public RedirectView addOrder(HttpServletRequest request, RedirectAttributes redir,
                                 PlaceOrderDTO orderDTO) {
        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                            " is trying to add an order");

        RedirectView redirectView;

        try{
            PlaceOrderDTOValidator.validate(orderDTO);

            if(orderDTO.paymentWithCard()) {
                currentOrder = orderDTO;

                redirectView = new RedirectView("/order_payment",true);
                return redirectView;
            }

            OrderDetailsForEmailDTO orderDetails = orderService.add(orderDTO);

            rabbitMQOrderSender.send(orderDetails);

            redirectView = new RedirectView("/home",true);
        }
        catch(InvalidDataException e) {
            logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                                        " cannot add order " + e.getMessage());

            redirectView = new RedirectView("/placeOrder", true);
            redir.addFlashAttribute("exception", e.getMessage());
        }
        catch(InsufficientStockException e) {
            logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                    " cannot add order " + e.getMessage());

            redirectView = new RedirectView("/cart", true);
            redir.addFlashAttribute("exception", e.getMessage());
        }

        return redirectView;
    }

    /**
     *
     * @return a page containing all credits cards of authenticated client
     */
    @GetMapping("/order_payment")
    public ModelAndView getCreditCards() {
        logger.log(Level.INFO, "/order_payment page accessed");

        ModelAndView mav = new ModelAndView();

        UserDTO userDTO = userAuthentication.getAuthenticatedUser();
        List<CreditCardDetailsDTO> creditCardDetailsDTOs = creditCardService.findByUserId(userDTO.getId());

        mav.addObject("credit_cards", creditCardDetailsDTOs);
        mav.addObject("user", userDTO);

        mav.setViewName("order_payment");

        return mav;
    }

    /**
     * Add a new order
     * @param idCreditCard id of desired credit card
     * @return home page if order was successfully created and paid, otherwise the page where client completes the
     * requested data and corresponding error message.
     */
    @PostMapping("/addOrderExistingCard")
    public RedirectView addOrderExistingCard(HttpServletRequest request, RedirectAttributes redir,
                                             String idCreditCard) {
        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                " is trying to add an order and pay it with an existing card");

        RedirectView redirectView;

        try{

            if(BooleanUtils.isFalse(creditCardService.existsById(idCreditCard)))
                throw new InvalidDataException(WrongMessage.INVALID_CREDIT_CARD_ID);

            PlaceOrderDTOValidator.validate(currentOrder);
            currentOrder.setPaid(true);
            OrderDetailsForEmailDTO orderDetails = orderService.add(currentOrder);

            rabbitMQOrderSender.send(orderDetails);

            currentOrder = null;

            redirectView = new RedirectView("/home",true);
        }
        catch(InvalidDataException e) {
            logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                    " cannot add order or pay it with an existing card " + e.getMessage());

            redirectView = new RedirectView("/order_payment", true);
            redir.addFlashAttribute("exception", e.getMessage());
        }
        catch(InsufficientStockException e) {
            logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                    " cannot add order " + e.getMessage());

            redirectView = new RedirectView("/cart", true);
            redir.addFlashAttribute("exception", e.getMessage());
        }

        return redirectView;
    }

    /**
     * Add a new order and a new credit card
     * @param creditCardDTO data about the new credit card
     * @return home page if order was successfully created and paid, credit card was successfully added,
     * otherwise the page where client completes the requested data and corresponding error message.
     */
    @PostMapping(value = "/addOrderNewCard", params = "save")
    public RedirectView  addOrderSaveCard(HttpServletRequest request, RedirectAttributes redir,
                                          CreditCardDTO creditCardDTO) {
        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                " is trying to add an order, pay it and save the used credit card");

        RedirectView redirectView;

        try{
            CreditCardDTOValidator.validate(creditCardDTO);
            creditCardService.add(creditCardDTO);

            PlaceOrderDTOValidator.validate(currentOrder);
            currentOrder.setPaid(true);
            OrderDetailsForEmailDTO orderDetails = orderService.add(currentOrder);

            rabbitMQOrderSender.send(orderDetails);

            currentOrder = null;

            redirectView = new RedirectView("/home",true);
        }
        catch(InvalidDataException e) {
            logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                    " cannot add order or pay it or save the used credit card " + e.getMessage());

            redirectView = new RedirectView("/order_payment", true);
            redir.addFlashAttribute("exception", e.getMessage());
        }
        catch(InsufficientStockException e) {
            logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                    " cannot add order " + e.getMessage());

            redirectView = new RedirectView("/cart", true);
            redir.addFlashAttribute("exception", e.getMessage());
        }

        return redirectView;
    }

    /**
     * Add a new order and credit. The credit card used will not be added.
     * @param creditCardDTO data about the new credit card
     * @return home page if order was successfully created and paid, otherwise the page where client
     * completes the requested data and corresponding error message.
     */
    @PostMapping(value = "/addOrderNewCard", params = "doNotSave")
    public RedirectView addOrderNewCard(HttpServletRequest request, RedirectAttributes redir,
                                        CreditCardDTO creditCardDTO) {
        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                " is trying to add an order and pay it");

        RedirectView redirectView;

        try{
            CreditCardDTOValidator.validate(creditCardDTO);

            PlaceOrderDTOValidator.validate(currentOrder);
            currentOrder.setPaid(true);
            OrderDetailsForEmailDTO orderDetails = orderService.add(currentOrder);

            rabbitMQOrderSender.send(orderDetails);

            currentOrder = null;

            redirectView = new RedirectView("/home",true);
        }
        catch(InvalidDataException e) {
            logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                    " cannot add order or pay it " + e.getMessage());


            redirectView = new RedirectView("/order_payment", true);
            redir.addFlashAttribute("exception", e.getMessage());
        }
        catch(InsufficientStockException e) {
            logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                    " cannot add order " + e.getMessage());

            redirectView = new RedirectView("/cart", true);
            redir.addFlashAttribute("exception", e.getMessage());
        }

        return redirectView;
    }

    /**
     *
     * @return a page containing details about authenticated user orders
     */
    @GetMapping("/client_orders")
    public ModelAndView getClientOrders() {
        logger.log(Level.INFO, "/client_orders page accessed");

        ModelAndView mav = new ModelAndView();

        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        List<OrderDTO> orderDTOs = orderService.findByClientId(userDTO.getId());

        mav.addObject("orders", orderDTOs);
        mav.addObject("user", userDTO);

        mav.setViewName("client_orders");
        return mav;
    }

    /**
     *
     * @param id id of order to be displayed
     * @return a page containing details about the selected order
     */
    @GetMapping("/order_details/{id}")
    public ModelAndView getOrderDetails(@PathVariable("id") String id) {
        logger.log(Level.INFO, "/order_details/" + id + " page accessed");

        ModelAndView mav = new ModelAndView();

        OrderDetailsDTO orderDetailsDTO = orderService.find(id);

        mav.addObject("order", orderDetailsDTO);
        mav.addObject("user", userAuthentication.getAuthenticatedUser());

        mav.setViewName("order_details");
        return mav;
    }

    /**
     *
     * @return a page containing all placed orders
     */
    @GetMapping("/admin/orders")
    public ModelAndView getAllOrders() {
        logger.log(Level.INFO, "/admin/orders page accessed");

        ModelAndView mav = new ModelAndView();

        List<OrderDetailsDTO> orderDetailsDTOs = orderService.findAll();

        mav.addObject("orders", orderDetailsDTOs);
        mav.addObject("user", userAuthentication.getAuthenticatedUser());

        mav.setViewName("orders");

        return mav;
    }

    /**
     * Delete order with desired id
     * @param id id of order to be deleted
     * @return a page containing all placed orders
     */
    @GetMapping("/admin/deleteOrder/{id}")
    public ModelAndView deleteOrder(@PathVariable("id") String id) {
        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        logger.log(Level.INFO, "Admin: " + userDTO.getEmail() +
                                " is trying to delete order " + id);

        orderService.delete(id);

        return new ModelAndView("redirect:/admin/orders");
    }

    /**
     * Generate a PDF invoice
     * @param id order id for which the invoice will be generated
     * @param response http response
     */
    @GetMapping("/download-invoice-pdf/{id}")
    public void downloadInvoicePdf(@PathVariable("id") String id, HttpServletResponse response) {
        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                " is trying to download a PDF invoice for order " + id);

        try {
            OrderInvoiceDTO orderInvoiceDTO = orderService.findForInvoice(id);

            InvoiceService invoiceService = new InvoiceService(pdfInvoice);

            Path file = Paths.get(invoiceService.generateInvoice(orderInvoiceDTO).getAbsolutePath());

            if (Files.exists(file)) {
                response.setContentType("application/pdf");
                response.addHeader("Content-Disposition",
                        "attachment; filename=" + file.getFileName());
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }

            logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                    " successfully downloaded a PDF invoice for order " + id);
        }
        catch(IOException e) {
            logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                    " cannot download a PDF invoice for order " + id + " " + e.getMessage());
        }

    }

    /**
     * Generate a TXT invoice
     * @param id order id for which the invoice will be generated
     * @param response http response
     */
    @GetMapping("/download-invoice-txt/{id}")
    public void downloadInvoiceTxt(@PathVariable("id") String id, HttpServletResponse response) {
        UserDTO userDTO = userAuthentication.getAuthenticatedUser();

        logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                " is trying to download a TXT invoice for order " + id);

        try {
            OrderInvoiceDTO orderInvoiceDTO = orderService.findForInvoice(id);

            InvoiceService invoiceService = new InvoiceService(txtInvoice);

            Path file = Paths.get(invoiceService.generateInvoice(orderInvoiceDTO).getAbsolutePath());

            if (Files.exists(file)) {
                response.setContentType("application/txt");
                response.addHeader("Content-Disposition",
                        "attachment; filename=" + file.getFileName());
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }

            logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                    " successfully downloaded a TXT invoice for order " + id);
        }
        catch(IOException e) {
            logger.log(Level.INFO, "Client: " + userDTO.getEmail() +
                    " cannot download a PDF invoice for order " + id + " " + e.getMessage());
        }
    }

}
