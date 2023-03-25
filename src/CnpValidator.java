import java.time.*;
import java.util.*;

public class CnpValidator {
    private static final int lungime = 13;
    private static int[] YEAR_OFFSET = new int[] {
            0, 1900, 1900, 1800, 1800, 2000, 2000
    };
    private static int[] getDigits(String cnp){
        int[] digits = new int[lungime];
        for(int i = 0; i < lungime; i++){
            char c = cnp.charAt(i);
            if(!Character.isDigit(c)){
                return null;
            }
            digits[i] = (byte) Character.digit(c, 10);
        }
        return digits;
    }

    public String valideazaCNP(String cnp){
        if(cnp.length() != lungime)
            return "CNP-ul are un numarul invalid de caractere";

        int[] cnpDigits = getDigits(cnp);
        if(cnpDigits == null)
            return "CNP-ul trebuie sa fie format doar din cifre";

        int luna = cnpDigits[3] * 10 + cnpDigits[4];
        if(luna < 1 || luna > 12)
            return "Luna este invalida";

        int zi = cnpDigits[5] * 10 + cnpDigits[6];
        if(zi < 1)
            return "Ziua este invalida";

        int an;
        if(cnpDigits[0] == 1 || cnpDigits[0] == 2 || cnpDigits[0] == 5 || cnpDigits[0] == 6) {
            an = YEAR_OFFSET[cnpDigits[0]] + cnpDigits[1] * 10 + cnpDigits[2];
            GregorianCalendar calendar = new GregorianCalendar(an, luna-1, zi);

            int ziMaxLuna = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            if(zi > ziMaxLuna)
                return "Ziua este invalida";
            if(luna == 2 && zi > 29)
                return "Ziua este invalida";
            if(luna == 2 && an % 4 != 0 && zi == 29)
                return "Ziua este invalida";


            LocalDate birthDate = LocalDate.of(an, luna, zi);
            LocalDate currentDate = LocalDate.now();
            if(Period.between(birthDate, currentDate).isNegative())
                return "Data de nastere este invalida";

            if(Period.between(birthDate, currentDate).getYears() < 18)
                return "Clientul trebuie sa aiba cel putin 18 ani";
        }
        else return "Genul este invalid";
        return "";
    }
}
