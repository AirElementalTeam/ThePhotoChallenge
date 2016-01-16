package Common;

import android.app.Activity;

public class Validator {

    final String INVALID_CREDENTIALS_TITLE = "Hmmmm...";
    final String INVALIS_CREDENTIALS_MSG = "Looks like your email or password (or both) are incorrect.";
    final String INVALID_EMAIL_TITLE = "Invalid email address!";
    final String INVALID_EMAIL_MSG = "This really doesn't look like a real email...";
    final String EMPTY_EMAIL_MSG = "              Empty? No!";
    final String INVALID_NAME_TITLE = "Don't have a name?";
    final String EMPTY_NAME_MSG = "You sure have one:) Please tell us how how should we address you?";
    final String INVALID_PASS_TITLE = "Invalid password!";
    final String EMPTY_PASS_MSG = "Password cannot be empty.";
    final String MISMATCH_PASS_MSG = "Those two password sure don't look the same to me.";
    final String INVALID_PASS_MSG = "Passwords should between 4 and 10 alphanumeric characters. Sorry, try again.";
    final String TERRIBLE_ERROR_TITLE = "Oh no!";
    final String TERRIBLE_ERROR_MSG = "Some terrible error happened... We are very sorry! Try again.";
    final String EMPTY_TEXT_IN_CHALLENGE = "All fields must be filled";

    private Activity activity;
    private MessageDialog errorMessage;

    public Validator(Activity activity){
        this.activity = activity;
        errorMessage = new MessageDialog(activity);
    }

    public Activity getActivity() {
        return this.activity;
    }

    public void invalidCredentials(){
        errorMessage.setTitle(INVALID_CREDENTIALS_TITLE);
        errorMessage.setMessage(INVALIS_CREDENTIALS_MSG);
        errorMessage.show();
    }

    public boolean validateLogin(String email, String password){
        if(email.isEmpty()){
            errorMessage.setTitle(INVALID_EMAIL_TITLE);
            errorMessage.setMessage(EMPTY_EMAIL_MSG);
            errorMessage.show();
            return false;
        }
        if(password.isEmpty()){
            errorMessage.setTitle(INVALID_PASS_TITLE);
            errorMessage.setMessage(EMPTY_PASS_MSG);
            errorMessage.show();
            return false;
        }
        return true;
    }

    public boolean validateRegister(String email, String name, String password, String passwordConfirm){
        if(!password.equals(passwordConfirm)){
            System.out.println("Inside IF PASSWORDS ARE NOT EQUAL");
            errorMessage.setTitle(INVALID_PASS_TITLE);
            errorMessage.setMessage(MISMATCH_PASS_MSG);
            errorMessage.show();
            return false;
        }

        else if(email.isEmpty()){
            System.out.println("Inside EMPTY EMAIL");
            errorMessage.setTitle(INVALID_EMAIL_TITLE);
            errorMessage.setMessage(EMPTY_EMAIL_MSG);
            errorMessage.show();
            return false;
        }

        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            System.out.println("Inside INVALID EMAIL ADDRESS");
            errorMessage.setTitle(INVALID_EMAIL_TITLE);
            errorMessage.setMessage(INVALID_EMAIL_MSG);
            errorMessage.show();
            return false;
        }

        //TODO: is this how we validate passwords?
        else if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            System.out.println("Inside PASSWORD LENGTH VALIDATION");
            errorMessage.setTitle(INVALID_PASS_TITLE);
            errorMessage.setMessage(INVALID_PASS_MSG);
            errorMessage.show();
            return false;
        }

        else if(name.isEmpty()){
            System.out.println("Inside EMPTY NAME");
            errorMessage.setTitle(INVALID_NAME_TITLE);
            errorMessage.setMessage(EMPTY_NAME_MSG);
            errorMessage.show();
            return false;
        }
        else {
            return true;
        }
    }

    public void TerribleError(){
        errorMessage.setTitle(TERRIBLE_ERROR_TITLE);
        errorMessage.setMessage(TERRIBLE_ERROR_MSG);
        errorMessage.show();
        return;
    }

    public boolean validateNewChallenge(String title, String theme, String dueDate) {
        //TODO: more friendly response here
        if(title.isEmpty() || theme.isEmpty() ||dueDate.isEmpty() || dueDate.equals("Set due date")){
            errorMessage.setTitle(null);
            errorMessage.setMessage(EMPTY_TEXT_IN_CHALLENGE);
            errorMessage.show();
            return false;
        }
        return true;
    }
}
