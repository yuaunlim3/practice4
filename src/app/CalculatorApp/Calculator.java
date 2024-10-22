package app.CalculatorApp;

import java.util.*;

public class Calculator{
    protected ArrayList<String> operations;
    protected int result;

    public Calculator(){
        this.operations = new ArrayList<String>();
        this.result = 0;
    }
    public int calculate(String op){
        String[] opArray = op.split(" ");
        for (String item : opArray) {
            this.operations.add(item);
        }
        while(this.operations.size() > 1){
            if(this.operations.contains("(")){
                int indexOpen = this.operations.indexOf("(");
                int indexClose = this.operations.indexOf(")");
                List<String> inBrackets = this.operations.subList(indexOpen + 1, indexClose);
                Object[] brackArrays = inBrackets.toArray();
                String brackString = brackArrays.toString();
                Calculator brackCalculator = new Calculator();
                brackCalculator.calculate(brackString);
                
            }
            if(this.operations.contains("%")){
                int index = this.operations.indexOf("%");
                int result = getNum(this.operations.get(index - 1)) % getNum(this.operations.get(index + 1));
                this.updateOperations(index, result);
            }else if(this.operations.contains("/")){
                int index = this.operations.indexOf("/");
                int result = getNum(this.operations.get(index - 1)) / getNum(this.operations.get(index + 1));
                this.updateOperations(index, result);
            }
            else if(this.operations.contains("*")){
                int index = this.operations.indexOf("*");
                int result = getNum(this.operations.get(index - 1)) * getNum(this.operations.get(index + 1));
                this.updateOperations(index, result);
            }
            else if(this.operations.contains("+")){
                int index = this.operations.indexOf("+");
                int result = getNum(this.operations.get(index - 1)) + getNum(this.operations.get(index + 1));
                this.updateOperations(index, result);
            }
            else if(this.operations.contains("-")){
                int index = this.operations.indexOf("-");
                int result = getNum(this.operations.get(index - 1)) - getNum(this.operations.get(index + 1));
                this.updateOperations(index, result);
            }
        }
        this.result = Integer.parseInt(this.operations.get(0));
        this.operations.clear();
        return this.result;
    }

    public int getNum(String input) {
        if ("last".equalsIgnoreCase(input.trim())) {
            return this.result; // Return the previous result stored in `this.result`
        } else {
            return Integer.parseInt(input); // Otherwise, parse the input as an integer
        }
    }

    private void updateOperations(int index, int result) {
        this.operations.set(index - 1, Integer.toString(result));
        this.operations.remove(index);  // Remove the operator
        this.operations.remove(index);  // Remove the right operand
    }
    
    public int getResult() {
        return this.result;
    }

    

}
