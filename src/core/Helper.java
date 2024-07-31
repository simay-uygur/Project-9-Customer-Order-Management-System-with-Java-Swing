package src.core;

import javax.swing.*;

public class Helper {

    public static void setTheme(){
        UIManager.getInstalledLookAndFeels();

        for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
            if(info.getName().equals("Nimbus")){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static boolean isFieldEmpty(JTextField field){
        return field.getText().trim().isEmpty();
    }

    public static boolean isFieldListEmpty(JTextField[] fields)
    {
        for(JTextField field : fields)
        {
            if(isFieldEmpty(field)) return true;
        }
        return false ;
    }

    public static void optionPaneDialogTR(){ //not necessary
        UIManager.put("OptionPane.okButtonText", "OKAY");
        UIManager.put("OptionPane.yesButtonText", "Yes");
        UIManager.put("OptionPane.noButtonText", "No");
    }

    public static boolean isEmailValid(String mail){
         // before @ and after @ . the dot after @
        if(mail == null || mail.trim().isEmpty()) return false;
        if(!mail.contains("@")) return false;
        String[] emails = mail.split("@");
        if(emails.length != 2) return false; //before@ and after@
        if(emails[0].trim().isEmpty() || emails[1].trim().isEmpty() ) return false;
        if(!emails[1].contains(".")) return false;

        return true;
    }

    public static void showMessage( String message){

        String msg;

        optionPaneDialogTR();
        String title = switch (message) {
            case "fill" -> {
                msg = "Please fill all of the areas. ";
                yield "Error!!";
            }
            case "done" -> {
                msg = "Done.";
                yield "Outcome";
            }
            case "error" -> {
                msg = "An error occured.";
                yield "Error";
            }
            default -> {
                msg = message;
                yield "Message ";
            }
        };

        JOptionPane.showMessageDialog(null, msg, title,JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String str){
        optionPaneDialogTR();
        String message ;
        if (str.equals("sure")){
            message = "Are you sure you want to do this process ?";
        } else {
            message = str;
        }
        return JOptionPane.showConfirmDialog(null, message, "Are you sure ?", JOptionPane.YES_NO_OPTION) == 0;
    }
}















