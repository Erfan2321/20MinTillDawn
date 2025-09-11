package com.Graphic.Model.Enum;

public enum Message {

                                                // common message
    Back          ("Back", "Dos"),
    Submit        ("Submit", "Soumettre"),

                                                // main menu
    startAsGuest  ("Start as guest", "Commencer en tant qu'invité"),

                                                // Sign up menu

    password      ("Password",                      "mot de passe"),
    SignUp        ("Sign up",                       "d'inscription"),
    enterName     ("Enter your name",               "Entrez votre nom"),
    SignUpTitle   ("Sign up menu",                  "Menu d'inscription"),
    successReg    ("successfully registered",       "enregistré avec succès"),
    playAsGuest1  ("play as guest",                 "jouer en tant qu'invité"),
    enterPass     ("Enter your password",           "entrez votre mot de passe"),
    UserNotFound  ("Username not found!",           "Le nom d'utilisateur est dé"),
    SecurityQ     ("What's your favorite singer?",  "Quel est ton animal préféré?"),
    emptyName     ("name can't be empty",           "le nom ne peut pas être vide"),
    IncorrectPass ("Password is incorrect",         "supprimer le compte ne peut pas"),
    weekPassword  ("password is week",              "le mot de passe est une semaine"),
    usernameTaken ("The username is already taken", "Le nom d'utilisateur est déjà pris"),
    emptyPass     ("password can't be empty",       "le mot de passe ne peut pas être vide"),
    emptySecQ     ("Security Question can't be empty","La question de sécurité ne peut pas être vide"),


    Login         ("Login", "de connexion"),
    LoginMenu     ("Login menu", "Menu de connexion"),
    WrongSecQ     ("wrong answer", "mauvaise réponse"),
    ForgetPass    ("Forget Pass", "Mot de passe oublié"),
    AnswerSecQ    ("Answer Security Question","La question de sécurité ne peut pas" ),


    ChangeAvatar  ("change Avatar",   "changer Avatar"),
    DeleteAccount ("delete Account",  "supprimer le compte"),
    ChangePassword("change Pass", "changer le mot de passe"),
    ChangeUsername("change name", "changer le nom d'utilisateur"),
    ChangeName    ("Username successfully changed", "changer le nom d'utilisateur"),

                                        // Hint menu

    Abilities     ("Abilities", "Capacités"),
    Cheats        ("Cheats", "tricher"),
    Heroes        ("Heroes", "Héros"),
    Keys          ("Keys", "clés"),



    ;

    private final String english;
    private final String french;



    Message (String english, String french) {
        this.english = english;
        this.french = french;
    }

    public String getMessage(String lang) {
        if (lang.equals("English"))
            return this.english;
        return this.french;
    };
}
