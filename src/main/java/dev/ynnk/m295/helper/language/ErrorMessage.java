package dev.ynnk.m295.helper.language;

public final class ErrorMessage {

    public static final Language LANGUAGE = Language.ENGLISH;

    private ErrorMessage(){}

    public static String notFoundById(String friendlyName, long id) {
        return String.format("There doesnt exist a %s with the id %s in the Database!", friendlyName, id);
    }

    public static String noObjectPresent(String friendlyName){
        return "You need to provide a " + friendlyName;
    }

    public static String support(long errorId){
        return "The server had a problem processing the request." +
                " Please try it later again or contact the support with the following error code "  + errorId;
    }

}
