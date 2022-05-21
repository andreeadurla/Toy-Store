package ro.sd.a2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.PeriodDTO;
import ro.sd.a2.dto.SoldProductsDTO;
import ro.sd.a2.entity.OrderItem;
import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.repository.OrderItemRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private static final Logger logger = Logger.getLogger(OrderItemService.class.getName());

    @Autowired
    private final OrderItemRepository orderItemRepository;

    /**
     * Find all sold order items from a specified period
     * @param period period in which products were sold
     * @return SoldProductsDTO which contains necessary information about the sold products
     * @throws InvalidDataException if period is invalid
     */
    public SoldProductsDTO findAllSoldProducts(PeriodDTO period) {

        Date startDate = period.getStartDate();
        Date endDate = period.getEndDate();

        logger.log(Level.INFO, "Search all sold products from period " +
                startDate + " - " + endDate);

        List<OrderItem> orderItems = orderItemRepository.findAllByDate(startDate, endDate);

        Map<String, Integer> soldProducts = orderItems.stream()
                                                    .collect(Collectors.groupingBy(OrderItem::getProductName,
                                                            Collectors.summingInt(OrderItem::getQuantity)));

        SoldProductsDTO soldProductsDTO = SoldProductsDTO.builder()
                .startDate(period.getStartDate())
                .endDate(period.getEndDate())
                .products(soldProducts)
                .build();

        logger.log(Level.INFO, "Return all sold products from period " +
                startDate + " - " + endDate);

        return soldProductsDTO;
    }
}
