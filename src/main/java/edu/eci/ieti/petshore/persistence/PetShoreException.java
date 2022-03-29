package edu.eci.ieti.petshore.persistence;

public class PetShoreException extends Exception {
    public final static String USERNAME_NOT_FOUND = "The user that you want to enter is not available";

    public final static String PRODUCT_NOT_FOUND = "The product selected is not available";

    public final static String INVALID_PRODUCT = "The product selected is invalid";

    public final static String INVALID_RATING = "The value of rating is invalid";

    public final static String NEIGBORHOOD_NOT_FOUND = "The neighborhood selected is not available";

    public final static String INVALID_NEIGHBORHOOD = "The neighborhood is invalid";

    public final static String CLIENT_NOT_FOUND = "The client selected is not available";

    public final static String SELLER_NOT_FOUND = "The seller selected is does not exist";

    public final static String SELLER_NOT_AVAILABLE = "The seller selected is not available";

    public PetShoreException(String message){
        super(message);
    }
}
