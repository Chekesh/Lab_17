import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static double addition(double arg1, double arg2) {return arg1 + arg2;}
    public static double subtraction(double arg1, double arg2) {return arg1 - arg2;}

    public static double multiplication(double arg1, double arg2) {return arg1 * arg2;}

    public static double division(double arg1, double arg2) {return arg1 / arg2;}

    public static String[] peremen(String[] massiv, int i, double result, int proshI){
        massiv[proshI-1]=null;
        massiv[proshI]=null;
        massiv[i+1]=Double.toString(result);
        return massiv;
    }
    public static int kray(String[] massiv, int i){
        boolean znach=false;
        while (znach==false) {
            if (massiv[i + 1] == null) {
                i++;
            }
            else {
                znach=true;
            }
        }
        return i;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true){
        String expression = in.next();
        //System.out.println(expression);
        String[] pox = expression.split("");

        int operathin = 0;
        int skob = 0;
        for (int i=0; i<pox.length;i++){
            if(pox[i].equals("+") || pox[i].equals("-") || pox[i].equals("*") || pox[i].equals("/")){
                if (i!=0&&(!(pox[i-1].equals("+") || pox[i-1].equals("-") || pox[i-1].equals("*") || pox[i-1].equals("/") || pox[i-1].equals("(")))){
                    operathin++;
                }
            }
            else if(pox[i].equals("(") || pox[i].equals(")")){        // находим сколько надо создать экземпляров
                skob++;
            }
        }

        String[] action = new String[(operathin*2)+1+skob];
        //System.out.println("pox "+Arrays.toString(pox));  //закоментировать
        //System.out.println("action "+ Arrays.toString(action));
        int index = 0;
        String ch="";
        for(int i=0;i<pox.length;i++) {
            if (pox[i].equals("+") || pox[i].equals("-") || pox[i].equals("*") || pox[i].equals("/") || pox[i].equals("(") || pox[i].equals(")")) {
                if (i!=0&&(pox[i].equals("(")||(!(pox[i-1].equals("+") || pox[i-1].equals("-") || pox[i-1].equals("*") || pox[i-1].equals("/") || pox[i-1].equals("("))))) {
                    action[index] = pox[i];
                    index++;
                } else if (i==0&&(pox[i].equals("(")||pox[i-1].equals("("))) {
                    action[index] = pox[i];
                    index++;
                } else{ch=ch+pox[i];}
            }
            else {
                if(i!=pox.length-1) {
                    if (pox[i + 1].equals("+") || pox[i + 1].equals("-") || pox[i + 1].equals("*") || pox[i + 1].equals("/") || pox[i + 1].equals("(") || pox[i + 1].equals(")")) {
                        ch = ch + pox[i];
                        action[index] = ch;
                        ch="";
                        index++;
                    } else {ch = ch + pox[i];}
                }
                else{
                    ch = ch + pox[i];                    // заполняем экземпляры
                    action[index] = ch;
                }
            }
        }

        //System.out.println("action: " +Arrays.toString(action));
        double result;
        int chetchik = (skob/2)+1;
        int proshI;
        String operannd;
        boolean skobkaNazad;
        double resultat=0;
        while(chetchik!=0) {
            //System.out.println("action: " +Arrays.toString(action));
            int right = action.length-1;
            int left = 0;
            //System.out.println("chetchik: "+chetchik);
            skobkaNazad = true;
            if (skob != 0) {
                for (int sk = 1; skobkaNazad; sk++) {
                    if (action[sk - 1] != null && action[sk - 1].equals("(")) {
                        left = sk - 1;
                    }
                    if (action[sk] != null && action[sk].equals(")")) {
                        skobkaNazad = false;
                        right = sk;
                    }
                }
                skob = skob - 2;
                //System.out.println("left "+left + "\n" + "right "+right);
            }
            for (int i=0;i<action.length;i++) {
                if (left < i && i < right) {
                    if (action[i] != null && action[i].equals("*")){
                        operannd = action[i - 1];
                        proshI = i;
                        i = kray(action, i);
                        result = multiplication(Double.parseDouble(operannd), Double.parseDouble(action[i+1]));
                        action=peremen(action,i, result, proshI);
                    }
                    else if(action[i] != null && action[i].equals("/")){
                        operannd = action[i - 1];
                        proshI = i;
                        i = kray(action, i);
                        result = division(Double.parseDouble(operannd), Double.parseDouble(action[i+1]));
                        action=peremen(action,i, result, proshI);
                    }
                }
            }
            for (int i=0;i<action.length;i++) {
                if (left < i && i < right) {
                    if (action[i] != null && action[i].equals("+")) {
                        operannd = action[i - 1];
                        proshI = i;
                        i = kray(action, i);
                        result = addition(Double.parseDouble(operannd), Double.parseDouble(action[i + 1]));
                        action = peremen(action, i, result, proshI);
                    }
                    else if (action[i] != null && action[i].equals("-")) {
                        proshI = i;
                        operannd = action[i - 1];
                        i = kray(action, i);
                        result = subtraction(Double.parseDouble(operannd), Double.parseDouble(action[i + 1]));
                        action = peremen(action, i, result, proshI);
                    }
                }
            }
            if(chetchik==2){
                resultat=Double.parseDouble(action[right-1]);
            }
            if(chetchik!=1) {
                action[left] = null;
                action[right] = action[right - 1];
                action[right - 1] = null;
            }
            chetchik--;
        }
        if(action[action.length-1]!=null){
            resultat=Double.parseDouble(action[action.length-1]);
        }
        //System.out.println("action: " +Arrays.toString(action));
        System.out.println(resultat);
        }
    }
}