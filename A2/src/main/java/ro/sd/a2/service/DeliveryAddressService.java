package ro.sd.a2.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.DeliveryAddressDTO;
import ro.sd.a2.dtoValidator.DeliveryAddressDTOValidator;
import ro.sd.a2.entity.DeliveryAddress;
import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.message.WrongMessage;
import ro.sd.a2.repository.DeliveryAddressRepository;
import ro.sd.a2.validator.Validator;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryAddressService {

    private static final Logger logger = Logger.getLogger(DeliveryAddressService.class.getName());

    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    /**
     * Add a new delivery address. If already exists an address with the same details as
     * specified address, but its status is deleted, then its status is modified. If address
     * already exists and its id is equals with id of address from database, address details
     * will be updated. If address already exists, but its id is not equals with id of address
     * from database, then an exception is thrown. Otherwise, the new delivery address is added
     * in database.
     * @param deliveryAddressDTO data about new delivery address
     * @throws InvalidDataException if delivery address data are invalid or if already exists an address
     * with the same details (id_client, name, phone, county, city, address).
     */
    public void add(DeliveryAddressDTO deliveryAddressDTO) {

        DeliveryAddressDTOValidator.validate(deliveryAddressDTO);

        String id_client = deliveryAddressDTO.getId_client();
        String firstName = deliveryAddressDTO.getFirstName();
        String lastName = deliveryAddressDTO.getLastName();
        String phone = deliveryAddressDTO.getPhone();
        String county = deliveryAddressDTO.getCounty();
        String city = deliveryAddressDTO.getCity();
        String address = deliveryAddressDTO.getAddress();

        DeliveryAddress deliveryAddress = deliveryAddressDTO.toEntity();
        DeliveryAddress existingDeliveryAddress =
                deliveryAddressRepository.findByClient_idAndFirstNameAndLastNameAndPhoneAndCountyAndCityAndAddress(
                        id_client, firstName, lastName, phone,
                        county, city, address);

        if(ObjectUtils.isNotEmpty(existingDeliveryAddress)) {
            //update
            if(deliveryAddress.getId().equals(existingDeliveryAddress.getId())) {
                deliveryAddressRepository.save(deliveryAddress);
                logger.log(Level.INFO, "Delivery address with id " + deliveryAddress.getId() +
                                            " successfully edited");
                return ;
            }

            //add
            boolean deleted = existingDeliveryAddress.isDeleted();

            if(BooleanUtils.isTrue(deleted)) {
                deliveryAddress.setId(existingDeliveryAddress.getId());
                deliveryAddressRepository.save(deliveryAddress);
                logger.log(Level.INFO, "Delivery address with id " + deliveryAddress.getId() +
                                        " successfully added; modified status");
                return ;
            }
            else {
                throw new InvalidDataException(WrongMessage.WRONG_DELIVERY_ADDRESS);
            }
        }

        deliveryAddressRepository.save(deliveryAddress);
        logger.log(Level.INFO, "Delivery address with id " + deliveryAddress.getId() + " successfully added");

    }

    /**
     * Find delivery address with specified id
     * @param id id of delivery address to be found
     * @throws InvalidDataException if delivery address id is invalid
     * @return a DeliveryAddressDTO containing necessary information about the found address
     */
    public DeliveryAddressDTO find(String id) {

        Validator.isNotEmpty(id);

        logger.log(Level.INFO, "Search delivery address with id " + id);

        DeliveryAddress deliveryAddress = deliveryAddressRepository.getOne(id);

        if(ObjectUtils.isEmpty(deliveryAddress)) {
            logger.log(Level.INFO, "Delivery address with id " + id + " not found");
            return null;
        }

        logger.log(Level.INFO, "Delivery address with id " + id + " found");

        return DeliveryAddressDTO.fromEntity(deliveryAddress);
    }

    /**
     * Find all delivery addresses of the client with specified id
     * @param id_client id of client to which the delivery addresses belong
     * @throws InvalidDataException if client id is invalid
     * @return a list of DeliveryAddressDTO which contains necessary information about the
     * found addresses
     */
    public List<DeliveryAddressDTO> findByUserId(String id_client) {

        Validator.isNotEmpty(id_client);

        logger.log(Level.INFO, "Search all delivery addresses corresponding to" +
                                    "client " + id_client);

        List<DeliveryAddress> delivery_addresses = deliveryAddressRepository.findByClientId(id_client);

        List<DeliveryAddressDTO> deliveryAddressDTOs = delivery_addresses.stream()
                                                                        .map(a -> DeliveryAddressDTO.fromEntity(a))
                                                                        .collect(Collectors.toList());

        logger.log(Level.INFO, "Return all delivery addresses corresponding to" +
                                    "client " + id_client);

        return  deliveryAddressDTOs;
    }

    /**
     * Delete delivery address with specified id
     * @param id id of delivery address to be deleted
     * @throws InvalidDataException if delivery address id is invalid
     */
    public void delete(String id) {

        Validator.isNotEmpty(id);

        deliveryAddressRepository.deleteById(id);

        logger.log(Level.INFO, "Delivery address with id " + id + " successfully deleted");
    }
}
