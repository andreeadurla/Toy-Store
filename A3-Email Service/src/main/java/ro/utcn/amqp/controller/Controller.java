package ro.utcn.amqp.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.utcn.amqp.dto.MessageDTO;
import ro.utcn.amqp.dto.UserDTO;
import ro.utcn.amqp.dtoValidator.UserDTOValidator;
import ro.utcn.amqp.entity.Email;
import ro.utcn.amqp.exception.InvalidDataException;
import ro.utcn.amqp.generator.CreateAccountEmail;
import ro.utcn.amqp.message.WrongMessage;
import ro.utcn.amqp.service.EmailSenderService;

@RestController
@RequestMapping("/email")
public class Controller {

    @Autowired
    private EmailSenderService senderService;

    private final String authorizationToken = "Bearer " + "37452b9d-f19d-4b93-95fd-360e958a4bc2" + "89c41799-3125-4432-955c-d576fee11687";

    @PostMapping("/generateEmail")
    public ResponseEntity<MessageDTO> generateEmail(@RequestHeader(value="Authorization") String authorizationHeader,
                                                    @RequestBody UserDTO userDTO){

        try {
            UserDTOValidator.validate(userDTO);

            if(!StringUtils.equals(authorizationToken, authorizationHeader)) {
                throw new InvalidDataException(WrongMessage.INVALID_TOKEN);
            }

            CreateAccountEmail createAccountEmail = new CreateAccountEmail();
            Email email = createAccountEmail.generate(userDTO);
            senderService.sendEmail(email);

            MessageDTO messageDTO = MessageDTO.builder().message("Email successfully send").build();
            return ResponseEntity.status(HttpStatus.OK).body(messageDTO);
        }
        catch(Exception e) {
            MessageDTO messageDTO = MessageDTO.builder().message(e.getMessage()).build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageDTO);
        }

    }

}
