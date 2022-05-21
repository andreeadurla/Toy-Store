package ro.sd.a2.message;

public class WrongMessage {

    public static final String INVALID_NAME = "Invalid name, please try again!";

    public static final String INVALID_EMAIL = "Invalid email, please try again!";

    public static final String INVALID_PASSWORD = "Invalid password:\nMust contain at least 1 lowercase alphabetical character\n" +
            "Must contain at least 1 uppercase alphabetical character\n" +
            "Must contain at least 1 numeric character\n" +
            "Must contain at least one special character\n" +
            "Must be eight characters or longer";

    public static final String INVALID_PHONE_NUMBER = "Invalid phone number, please try again!";

    public static final String WRONG_EMAIL_OR_PASSWORD = "Wrong email or password, please try again!";

    public static final String WRONG_PASSWORD = "Password and confirm password are different, please try again!";

    public static final String WRONG_EMAIL = "This email address already exists, please try again!";

    public static final String WRONG_PHONE = "This phone number already exists, please try again!";

    public static final String INVALID_QUANTITY = "Quantity <= 0";

    public static final String INVALID_PRICE = "Price <= 0";

    public static final String WRONG_PRODUCT_NAME = "This product already exists, please try again!";

    public static final String WRONG_CATEGORY_NAME = "This category already exists, please try again!";

    public static final String WRONG_DELIVERY_ADDRESS = "This delivery address already exists, please try again!";

    public static final String INVALID_DELIVERY_ADDRESS = "You must select a delivery address!";

    public static final String INSUFFICIENT_STOCK = "Insufficient stock";

    public static final String WRONG_STATUS = "This account is disable!";

    public static final String EMPTY_FIELD = "Text field cannot be empty, please try again!";

    public static final String INVALID_URL = "Invalid image URL, please try again!";

    public static final String WRONG_NOCARD = "This number of card already exists, please try again!";

    public static final String INVALID_CREDIT_CARD_ID = "Credit card with specified id doesn't exists!";

    public static final String INVALID_NOCARD = "Card number must contain exactly 16 digits!";

    public static final String INVALID_CVV = "CVV must contain exactly 16 digits!";

    public static final String INVALID_EXPIRATION_DATE = "This card is expired!";

    public static final String INVALID_DATE = "Invalid date (yyyy-MM-dd)";

    public static final String INVALID_DATES = "End date must be a date after Start date";
}
