package com.marcosavard.commons.quiz.math;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ResultFinder {
    private List<Integer> numbers;
    private int result;
    private List<OperationStep> solution = new ArrayList<>();

    public ResultFinder(Random random) {
        List<Integer> candidates = new ArrayList<>();

        for (int i=1; i<=10; i++) {
            candidates.add(i);
            candidates.add(i);
        }

        candidates.addAll(List.of(25, 50, 75, 100));
        Collections.shuffle(candidates, random);
        numbers = candidates.subList(0, 6);
        List<Integer> copy = new ArrayList<>();

        do {
            copy.clear();
            copy.addAll(numbers);
            solution.clear();

            do {
                result = perform(solution, copy, random);
            } while (copy.size() > 1);

        } while ((result <= 100) || (result >= 1000));
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public int getResult() {
        return result;
    }

    public List<OperationStep> getSolution() {
        return solution;
    }

    private static int perform(List<OperationStep> operationSteps, List<Integer> list, Random random) {
        int result = 0;

        do {
            Operation operation = Operation.values()[random.nextInt(4)];

            if (operation == Operation.ADDITION) {
                result = add(operationSteps, list, random);
            } else if (operation == Operation.SUBSTRACTION) {
                result = substract(operationSteps, list, random);
            } else if (operation == Operation.MULTIPLICATION) {
                result = multiply(operationSteps, list, random);
            } else {
                result = divide(operationSteps, list, random);
            }
        } while (result == 0);

        return result;
    }

    private static int add(List<OperationStep> operationSteps, List<Integer> list, Random random) {
        int term0, term1, result = 0;
        int count = 0;
        List<Integer> copy;

        do {
            copy = new ArrayList<>(list);
            Collections.shuffle(copy, random);
            term0 = copy.remove(0);
            term1 = copy.remove(0);
            result = term0 + term1;
            count++;
        } while ((list.contains(result)) && count < 50);

        if (result != 0) {
            list.clear();
            list.addAll(copy);
            list.add(result);
            OperationStep step = OperationStep.of(term0, Operation.ADDITION, term1, result);
            operationSteps.add(step);
        }

        return result;
    }

    private static int substract(List<OperationStep> operationSteps, List<Integer> list, Random random) {
        int term0, term1, result = 0;
        int count = 0;
        List<Integer> copy;

        do {
            copy = new ArrayList<>(list);
            Collections.shuffle(copy, random);
            term0 = copy.remove(0);
            term1 = copy.remove(0);
            boolean positive = (term0 - term1) > 0;
            result = positive ? term0 - term1 : 0;
            count++;
        } while ((result == 0 || list.contains(result)) && count < 50);

        if (result != 0) {
            list.clear();
            list.addAll(copy);
            list.add(result);
            OperationStep step = OperationStep.of(term0, Operation.SUBSTRACTION, term1, result);
            operationSteps.add(step);
        }

        return result;
    }

    private static int multiply(List<OperationStep> operationSteps, List<Integer> list, Random random) {
        int term0, term1, result = 0;
        int count = 0;
        List<Integer> copy;

        do {
            copy = new ArrayList<>(list);
            Collections.shuffle(copy, random);
            term0 = copy.remove(0);
            term1 = copy.remove(0);
            boolean large = (term0 * term1) > 144;
            result = large ? 0 : term0 * term1;
            count++;
        } while ((result == 0 || list.contains(result)) && count < 50);

        if (result != 0) {
            list.clear();
            list.addAll(copy);
            list.add(result);
            OperationStep step = OperationStep.of(term0, Operation.MULTIPLICATION, term1, result);
            operationSteps.add(step);
        }

        return result;
    }

    private static int divide(List<OperationStep> operationSteps, List<Integer> list, Random random) {
        int term0, term1, result = 0;
        int count = 0;
        List<Integer> copy;

        do {
            copy = new ArrayList<>(list);
            Collections.shuffle(copy, random);
            term0 = copy.remove(0);
            term1 = copy.remove(0);
            boolean hasRemainder = (term0 % term1 != 0.0) || (term1 == 1);
            result = hasRemainder ? 0 : term0 / term1;
            count++;
        } while ((result == 0 || list.contains(result)) && count < 50);

        if (result != 0) {
            list.clear();
            list.addAll(copy);
            list.add(result);
            OperationStep step = OperationStep.of(term0, Operation.DIVISION, term1, result);
            operationSteps.add(step);
        }

        return result;
    }

    public String getNumberList() {
        List<String> list = new ArrayList<>();

        for (int number : numbers) {
            list.add(Integer.toString(number));
        }

        String joined = String.join(", ", list);
        return joined;
    }

    public enum Operation {
        ADDITION("+"),
        SUBSTRACTION("-"),
        MULTIPLICATION("×"),
        DIVISION("÷");
        private String symbol;

        Operation(String symbol) {
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return symbol;
        }
    }

    public static class OperationStep {
        private int term0, term1, result;
        private Operation operation;

        public static OperationStep of(int term0, Operation operation, int term1, int result) {
            return new OperationStep(term0, operation, term1, result);
        }

        private OperationStep(int term0, Operation operation, int term1, int result) {
            this.term0 = term0;
            this.term1 = term1;
            this.operation = operation;
            this.result = result;
        }

        @Override
        public String toString() {
            return MessageFormat.format("{0} {1} {2} = {3}", term0, operation, term1, result);
        }
    }
}
