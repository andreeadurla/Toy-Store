package ro.sd.a2.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.CreditCardDTO;
import ro.sd.a2.dto.CreditCardDetailsDTO;
import ro.sd.a2.dtoValidator.CreditCardDTOValidator;
import ro.sd.a2.entity.CreditCard;
import ro.sd.a2.exception.InvalidDataException;
import ro.sd.a2.message.WrongMessage;
import ro.sd.a2.repository.CreditCardRepository;
import ro.sd.a2.validator.Validator;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditCardService {

    private static final Logger logger = Logger.getLogger(CreditCardService.class.getName());

    @Autowired
    private CreditCardRepository creditCardRepository;

    /**
     * Add a new credit card. If card already exists, an exception will be thrown. Otherwise,
     * the new credit card will be insert in database.
     * @param creditCardDTO data about new credit card
     * @throws InvalidDataException if credit card data are invalid or if already exists a card
     * with the number.
     */
    public void add(CreditCardDTO creditCardDTO) {

        CreditCardDTOValidator.validate(creditCardDTO);

        boolean existsNoCard = creditCardRepository.existsByNoCard(creditCardDTO.getNoCard());

        if(BooleanUtils.isTrue(existsNoCard))
            throw new InvalidDataException(WrongMessage.WRONG_NOCARD);

        creditCardRepository.save(creditCardDTO.toEntity());

        logger.log(Level.INFO, "Credit card of client " + creditCardDTO.getIdClient() + " successfully added");

    }

    /**
     * Find all credit cards of the client with specified id
     * @param idClient id of client to which the credit cards belong
     * @throws InvalidDataException if client id is invalid
     * @return a list of CreditCardDetailsDTO which contains necessary information about the
     * found cards
     */
    public List<CreditCardDetailsDTO> findByUserId(String idClient) {

        Validator.isNotEmpty(idClient);

        logger.log(Level.INFO, "Search all credit cards corresponding to" +
                "client " + idClient);

        List<CreditCard> creditCards = creditCardRepository.findByClientId(idClient);

        List<CreditCardDetailsDTO> creditCardDetailsDTOs =
                            creditCards.stream()
                                    .map(c -> CreditCardDetailsDTO.fromEntity(c))
                                    .collect(Collectors.toList());

        logger.log(Level.INFO, "Return all credit cards corresponding to " +
                        "client " + idClient);

        return creditCardDetailsDTOs;
    }

    /**
     * Check for a credit card with desired id
     * @param id id of credit card
     * @return
     */
    public boolean existsById(String id) {

        Validator.isNotEmpty(id);

        logger.log(Level.INFO, "Search credit card with id " + id);

        return creditCardRepository.existsById(id);
    }

    /**
     * Delete credit card with specified id
     * @param id id of credit card to be deleted
     * @throws InvalidDataException if credit card id is invalid
     */
    public void delete(String id) {

        Validator.isNotEmpty(id);

        creditCardRepository.deleteById(id);

        logger.log(Level.INFO, "Credit card with id " + id + " successfully deleted");
    }
}
