import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Stack;

public class ONPCalculatorGUI extends JFrame implements ActionListener {


    // Pole tekstowe dla działania matematycznego
    private final JTextField expressionTextField;

    // Pole tekstowe dla wyniku działania matematycznego
    private final JTextField resultTextField;

    // Pole tekstowe dla wyniku działania matematycznego
    private final JTextField resultEvaluationTextField;

    // Przyciski z cyframi
    private final JButton button0;
    private final JButton button1;
    private final JButton button2;
    private final JButton button3;
    private final JButton button4;
    private final JButton button5;
    private final JButton button6;
    private final JButton button7;
    private final JButton button8;
    private final JButton button9;

    // Przyciski z operacjami matematycznymi
    private final JButton buttonPlus;
    private final JButton buttonMinus;
    private final JButton buttonMultiply;
    private final JButton buttonDivide;
    private final JButton buttonEqual;
    private final JButton buttonClean;

    // Przyciski z nawiasami
    private final JButton buttonOpenPar;
    private final JButton buttonClosePar;

    // Przyciski funkcji trygonometrycznych
    private final JButton buttonSin;
    private final JButton buttonCos;

    // Konstruktor klasy
    public ONPCalculatorGUI() {
        // Ustawienia okna głównego
        setTitle("ONP kalkulator");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Utworzenie panelu z przyciskami
        JPanel buttonPanel = new JPanel(new GridLayout(4, 5, 5, 5));

        // Dodanie przycisków z cyframi
        button0 = new JButton("0");
        button0.addActionListener(this);
        buttonPanel.add(button0);

        button1 = new JButton("1");
        button1.addActionListener(this);
        buttonPanel.add(button1);

        button2 = new JButton("2");
        button2.addActionListener(this);
        buttonPanel.add(button2);

        button3 = new JButton("3");
        button3.addActionListener(this);
        buttonPanel.add(button3);

        button4 = new JButton("4");
        button4.addActionListener(this);
        buttonPanel.add(button4);

        button5 = new JButton("5");
        button5.addActionListener(this);
        buttonPanel.add(button5);

        button6 = new JButton("6");
        button6.addActionListener(this);
        buttonPanel.add(button6);

        button7 = new JButton("7");
        button7.addActionListener(this);
        buttonPanel.add(button7);

        button8 = new JButton("8");
        button8.addActionListener(this);
        buttonPanel.add(button8);

        button9 = new JButton("9");
        button9.addActionListener(this);
        buttonPanel.add(button9);

        // Dodanie przycisków z operacjami matematycznymi
        buttonPlus = new JButton("+");
        buttonPlus.addActionListener(this);
        buttonPanel.add(buttonPlus);

        buttonMinus = new JButton("-");
        buttonMinus.addActionListener(this);
        buttonPanel.add(buttonMinus);

        buttonMultiply = new JButton("*");
        buttonMultiply.addActionListener(this);
        buttonPanel.add(buttonMultiply);

        buttonDivide = new JButton("/");
        buttonDivide.addActionListener(this);
        buttonPanel.add(buttonDivide);

        buttonOpenPar = new JButton("(");
        buttonOpenPar.addActionListener(this);
        buttonPanel.add(buttonOpenPar);

        buttonClosePar = new JButton(")");
        buttonClosePar.addActionListener(this);
        buttonPanel.add(buttonClosePar);

        // Dodanie przycisków z funkcjami trygonometrycznymi
        buttonSin = new JButton("s");
        buttonSin.addActionListener(this);
        buttonPanel.add(buttonSin);

        buttonCos = new JButton("c");
        buttonCos.addActionListener(this);
        buttonPanel.add(buttonCos);

        buttonEqual = new JButton("=");
        buttonEqual.addActionListener(this);
        buttonPanel.add(buttonEqual);

        buttonClean = new JButton("CLEAN");
        buttonClean.addActionListener(this);
        buttonPanel.add(buttonClean);

        // Utworzenie panelu z polami tekstowymi
        JPanel textPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        expressionTextField = new JTextField();
        textPanel.add(expressionTextField);
        resultTextField = new JTextField("Wyrażenie w ONP");
        resultTextField.setEditable(false);
        textPanel.add(resultTextField);
        resultEvaluationTextField = new JTextField("Wynik wyrażenia");
        resultEvaluationTextField.setEditable(false);
        textPanel.add(resultEvaluationTextField);

        // Dodanie paneli do okna głównego
        setLayout(new BorderLayout(5, 5));
        add(buttonPanel, BorderLayout.CENTER);
        add(textPanel, BorderLayout.NORTH);

        // Wyświetlenie okna głównego
        setVisible(true);
    }

    // Metoda obliczająca wynik działania matematycznego w ONP
    public static String ONPCalculator(String infix) {
        Stack<Character> stack = new Stack<>();
        String postfix = "";
        boolean isNumber = false;
        for (int i = 0; i < infix.length(); i++) {
            char ch = infix.charAt(i);
            if (Character.isDigit(ch)) {
                if (!isNumber) {
                    postfix += " ";
                    isNumber = true;
                }
                postfix += ch;
            } else {
                if (isNumber) {
                    postfix += " ";
                    isNumber = false;
                }
                if (ch == '(') {
                    stack.push(ch);
                } else if (ch == ')') {
                    while (!stack.isEmpty() && stack.peek() != '(') {
                        postfix += " " + stack.pop();
                    }
                    stack.pop();
                } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^' || ch == 's' || ch == 'c'  || ch == '(' || ch == ')') {
                    while (!stack.isEmpty() && precedence(ch) <= precedence(stack.peek())) {
                        postfix += " " + stack.pop();
                    }
                    stack.push(ch);
                }else {
                    throw new IllegalArgumentException("Invalid operator: " + ch);
                }
            }
        }
        if (isNumber) {
            postfix += " ";
        }
        while (!stack.isEmpty()) {
            postfix += " " + stack.pop();
        }
        return postfix;
    }


    public static double evaluateONP(String onp) {
        Stack<Double> stack = new Stack<>();
        for (int i = 0; i < onp.length(); i++) {
            char ch = onp.charAt(i);
            if (Character.isDigit(ch)) {
                StringBuilder num = new StringBuilder();
                num.append(ch);
                while (i +1< onp.length() && (Character.isDigit(onp.charAt(i+1)) || onp.charAt(i+1) == '.')) {
                    num.append(onp.charAt(i+1));
                    i++;
                }
                double val = Double.parseDouble(num.toString());
                stack.push(val);
                System.out.println(Arrays.toString(stack.toArray()));
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^' || ch == 's' || ch == 'c') {

                if (ch == 's'  || ch == 'c') {

                        double arg = stack.pop();
                        double result = evaluate(arg,ch);
                        stack.push(result);



                    }
                else {

                    double b = stack.pop();

                    double a = stack.isEmpty() ? 0 : stack.pop();
                    if ((ch != '\\') && b != 0 ){
                        double result = evaluate(a, b, ch);
                        stack.push(result);
                    } else {
                        throw new IllegalArgumentException("Dividing by zero: ");
                    }
                }
            }
        }
        double finalResult = stack.pop();
        return finalResult;
    }

    private static double evaluate(double a, double b, char operator) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
            case '^':
                return Math.pow(a, b);
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
    private static double evaluate( double a, char operator) {

            if(operator == 's'){
                return Math.sin(a);
            }
            if(operator == 'c'){
                return Math.cos(a);
            }
            else {
                throw new IllegalArgumentException("Invalid operator: " + operator);
            }

    }


    private static int precedence(char ch) {
        switch (ch) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
            case 's':
            case 'c':
                return 3;
            default:
                return -1;
        }
    }

// Metoda główna programu
    public static void main(String[] args) {
        // Uruchomienie aplikacji
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ONPCalculatorGUI();
            }
        });
    }

    // Metoda obsługująca zdarzenia ActionListenera
    public void actionPerformed(ActionEvent e) {
        //Pobranie źródła zdarzenia
        Object source = e.getSource();
        // Obsługa zdarzenia dla przycisków z cyframi
        if (source == button0 || source == button1 || source == button2 || source == button3 || source == button4 || source == button5 || source == button6 || source == button7 || source == button8 || source == button9) {
            String digit = ((JButton) source).getText();
            expressionTextField.setText(expressionTextField.getText() + digit);
        }

        // Obsługa zdarzenia dla przycisków z operacjami matematycznymi
        if (source == buttonPlus || source == buttonMinus || source == buttonMultiply || source == buttonDivide || source == buttonOpenPar  || source == buttonClosePar || source == buttonSin ||source == buttonCos) {
            String operator = ((JButton) source).getText();
            expressionTextField.setText(expressionTextField.getText() + operator);
        }

        // Obsługa zdarzenia dla przycisku równości
           if (source == buttonEqual) {
                    // Pobranie działania matematycznego w postaci ONP z pola tekstowego
                    String expression = expressionTextField.getText();

                    // Sprawdzenie, czy pole tekstowe z działaniem matematycznym nie jest puste
                    if (!expression.isEmpty()) {
                        try {
                            // Wykonanie działania matematycznego w postaci ONP i wyświetlenie wyniku w polu tekstowym
                            String result = ONPCalculator(expression);
                            //resultTextField.setText(Double.toString(result));
                            resultTextField.setText(result);
                            Double resultEvalution = evaluateONP(result);
                            resultEvaluationTextField.setText(Double.toString(resultEvalution));
                        } catch (IllegalArgumentException ex) {
                            // Wyświetlenie komunikatu o błędzie w przypadku niepoprawnego działania matematycznego
                            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
        }
        // Obsługa zdarzenia dla przycisku clean
        if (source == buttonClean) {
            // Usuniecie
            expressionTextField.setText(null);
            resultTextField.setText(null);
            resultEvaluationTextField.setText(null);

        }
    }}