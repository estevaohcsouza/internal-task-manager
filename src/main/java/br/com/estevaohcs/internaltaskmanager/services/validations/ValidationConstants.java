package br.com.estevaohcs.internaltaskmanager.services.validations;

public class ValidationConstants {

    public static final String FULL_NAME_REGEX = "^(?=.{1,50}$)[a-zA-ZÀ-ÖØ-öø-ÿ]{2,}(\\s+[a-zA-ZÀ-ÖØ-öø-ÿ]{2,})+$";

    public static final String TITLE_REGEX = "^(?=.*\\S)[\\s\\S]+$";

    public static final String OPTIONAL_TEXT_REGEX = "^$|^(?=.*\\S)[\\s\\S]+$";

}
