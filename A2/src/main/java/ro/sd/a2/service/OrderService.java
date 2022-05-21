package ro.sd.a2.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.*;
import ro.sd.a2.dtoValidator.PlaceOrderDTOValidator;
import ro.sd.a2.entity.ClientOrder;
import ro.sd.a2.entity.OrderItem;
import ro.sd.a2.entity.Product;
import ro.sd.a2.entity.ShoppingCartItem;
import ro.sd.a2.exception.InsufficientStockException;
import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.message.WrongMessage;
import ro.sd.a2.repository.*;
import ro.sd.a2.validator.Validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {

    private static final Logger logger = Logger.getLogger(OrderService.class.getName());

    @Autowired
    private final OrderItemRepository orderItemRepository;

    @Autowired
    private final ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    @Autowired
    private final ClientRepository clientRepository;

    /**
     * Add a new order. If products stock is sufficient then the order is placed, corresponding
     * shopping cart is emptied and products stock is updated.
     * @param orderDTO data about new order
     * @return OrderDetailsForEmailDTO which contains necessary information about the placed order
     * @throws InvalidDataException if order data are invalid or if products stock is insufficient.
     */
    public OrderDetailsForEmailDTO add(PlaceOrderDTO orderDTO) {

        PlaceOrderDTOValidator.validate(orderDTO);

        ClientOrder clientOrder = orderDTO.toEntity();

        List<ShoppingCartItem> shoppingCartItems = shoppingCartRepository.findByClient_id(orderDTO.getId_client());
        List<OrderItem> orderItems = new ArrayList<>();

        //check stock
        for(ShoppingCartItem item : shoppingCartItems) {

            int wantedQuantity = item.getQuantity();
            int existingQuantity = item.getProductQuantity();
            String productName = item.getProductName();

            if(wantedQuantity > existingQuantity) {
                shoppingCartRepository.delete(item);
                logger.log(Level.INFO, "Shopping cart item with id " + item.getId() + " deleted " +
                                            "(insufficient stock)");
                throw new InsufficientStockException(productName + " -> " + WrongMessage.INSUFFICIENT_STOCK);
            }

            orderItems.add(OrderItem.convertFrom(clientOrder.getId(), item));
        }

        //update stock
        List<Product> products = new ArrayList<>();
        for(ShoppingCartItem item : shoppingCartItems) {

            int wantedQuantity = item.getQuantity();
            Product product = item.getProduct();
            int existingQuantity = product.getQuantity();

            product.setLastSale(new Date());
            product.setQuantity(existingQuantity - wantedQuantity);

            products.add(product);
        }

        orderRepository.save(clientOrder);
        logger.log(Level.INFO, "Order with id " + clientOrder.getId() + " successfully added");

        orderItemRepository.saveAll(orderItems);
        logger.log(Level.INFO, "Order items for order with id " + clientOrder.getId() + " successfully added");

        productRepository.saveAll(products);
        logger.log(Level.INFO, "Stock of products successfully updated");

        shoppingCartRepository.deleteAll(shoppingCartItems);
        logger.log(Level.INFO, "Client's (" + orderDTO.getId_client() + ") " +
                                "shopping cart items deleted successfully");


        clientOrder.setClient(clientRepository.getOne(orderDTO.getId_client()));
        clientOrder.setDelivery_address(deliveryAddressRepository.getOne(orderDTO.getId_delivery_address()));
        clientOrder.setItems(orderItems);
        return OrderDetailsForEmailDTO.fromEntity(clientOrder);
    }

    /**
     * Find all orders of the client with specified id
     * @param id_client id of client to which the orders belong
     * @throws InvalidDataException if client id is invalid
     * @return a list of OrderDTO which contains necessary information about the found orders
     */
    public List<OrderDTO> findByClientId(String id_client) {

        Validator.isNotEmpty(id_client);

        logger.log(Level.INFO, "Search all orders corresponding to" +
                "client " + id_client);

        List<ClientOrder> clientOrders = orderRepository.findByClientId(id_client);

        List<OrderDTO> orderDTOs = clientOrders.stream()
                                        .map(o -> OrderDTO.fromEntity(o))
                                        .collect(Collectors.toList());

        logger.log(Level.INFO, "Return all orders corresponding to " +
                            "client " + id_client);

        return  orderDTOs;
    }

    /**
     * Find order with specified id
     * @param id id of order to be found
     * @throws InvalidDataException if order id is invalid
     * @return a OrderDetailsDTO containing necessary information about the found order
     */
    public OrderDetailsDTO find(String id) {

        Validator.isNotEmpty(id);

        logger.log(Level.INFO, "Search order with id " + id);

        ClientOrder order = orderRepository.getOne(id);

        if(ObjectUtils.isEmpty(order)) {
            logger.log(Level.INFO, "Order with id " + id + " not found");
            return null;
        }

        logger.log(Level.INFO, "Order with id " + id + " found");

        return OrderDetailsDTO.fromEntity(order);
    }

    /**
     * Find order with specified id, details required for printing related invoice
     * @param id id of order to be found
     * @throws InvalidDataException if order id is invalid
     * @return a OrderInvoiceDTO containing necessary information about the found order
     */
    public OrderInvoiceDTO findForInvoice(String id) {

        Validator.isNotEmpty(id);

        logger.log(Level.INFO, "Search order with id " + id);

        ClientOrder order = orderRepository.getOne(id);

        if(ObjectUtils.isEmpty(order)) {
            logger.log(Level.INFO, "Order with id " + id + " not found");
            return null;
        }

        logger.log(Level.INFO, "Order with id " + id + " found");

        return OrderInvoiceDTO.fromEntity(order);
    }

    /**
     * Find all placed orders
     * @return a list of OrderDetailsDTO which contains necessary information about the found orders
     */
    public List<OrderDetailsDTO> findAll() {

        logger.log(Level.INFO, "Search all orders");

        List<ClientOrder> orders = orderRepository.findAll();

        List<OrderDetailsDTO> orderDetailsDTOs = orders.stream()
                                                    .map(o -> OrderDetailsDTO.fromEntity(o))
                                                    .collect(Collectors.toList());

        logger.log(Level.INFO, "Return all orders");

        return orderDetailsDTOs;
    }

    /**
     * Delete order with specified id
     * @param id id of order to be deleted
     * @throws InvalidDataException if order id is invalid
     */
    public void delete(String id) {

        Validator.isNotEmpty(id);

        orderRepository.deleteById(id);

        logger.log(Level.INFO, "Order with id " + id + " successfully deleted");
    }
}
