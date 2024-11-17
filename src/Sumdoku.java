import java.util.Scanner;
/*
* N: 58838
* @author: Inalcidio Abdul Gulamo Lampeao
* */
public class Sumdoku {


    /*         size = 3
    *        (0) (1) (2)
    *   (0) | 1 | 2 | 3 |   1: (0,0)
    *   (1) | 4 | 5 | 6 |   5: (1,1)
    *   (2) | 7 | 8 | 9 |   9: (2,2)
    *
    * */

    // Funcao rowOfSquare
    public static int rowOfSquare(int square, int gridSize) {
        // divisao inteira para obtermos a linha
        return ((square - 1)/gridSize) + 1;
    }
    // Funcao columnOfSquare
    public static int columnOfSquare(int square, int gridSize){
        return ((square - 1) % gridSize) + 1;
    }

    // Funcao isValidPuzzle variante para verificar se uma grid e valida para formar um puzzle
    public static boolean isValidForPuzzle(SumdokuGrid grid) {
        if (grid == null) {
            return false;
        }

        int size = grid.size();
        // Verifica linhas e colunas
        for (int i = 1; i <= size; i++) {
            if (!containsAllNumbers(getRow(grid, i), size) || !containsAllNumbers(getColumn(grid, i), size)) {
                return false;
            }
        }

        return true;
    }

    // Overload do isValidForPuzzle, este para verificar se um grupo e valido para formar um puzzle
    public static boolean isValidForPuzzle(GridGroups groups) {
        if (groups == null) {
            return false;
        }

        int size = groups.gridSize();

        // Cada square deve pertencer a algum grupo
        for (int row = 1; row <= size; row++) {
            for (int column = 1; column <= size; column++) {
                if (groups.groupOfSquare(row, column) == 0) {
                    return false;
                }
            }
        }

        // Verifica se os grupos nao estao vazios
        for (int group = 1; group <= groups.numberOfGroups(); group++) {
            if (!groupHasMembers(groups, group)) {
                return false;
            }
        }

        return true;
    }

    // Metodo auxiliar para obter uma linha
    private static int[] getRow(SumdokuGrid grid, int row) {
        int size = grid.size();
        int[] result = new int[size];
        for (int col = 1; col <= size; col++) {
            result[col - 1] = grid.value(row, col);
        }
        return result;
    }

    // Metodo auxiliar para obter uma coluna do grid
    private static int[] getColumn(SumdokuGrid grid, int col) {
        int size = grid.size();
        int[] result = new int[size];
        for (int row = 1; row <= size; row++) {
            result[row - 1] = grid.value(row, col);
        }
        return result;
    }

//     Metodo auxiliar que verifica se um array contem todos os numeros de 1 a n sem repeticoes
    private static boolean containsAllNumbers(int[] array, int size) {
        boolean[] seen = new boolean[size + 1];
        for (int num : array) {
            if (num < 1 || num > size || seen[num]) {
                return false;
            }
            seen[num] = true;
        }
        return true;
    }

    // metodo auxiliar para verificar se um grupo tem membros
    private static boolean groupHasMembers(GridGroups groups, int group) {
        int size = groups.gridSize();
        for (int row = 1; row <= size; row++) {
            for (int col = 1; col <= size; col++) {
                if (groups.groupOfSquare(row, col) == group) {
                    return true;
                }
            }
        }
        return false;
    }

    // Funcao definesPuzzle
    public static boolean definesPuzzle(SumdokuGrid grid, GridGroups groups) {
        // Valida se a grid e os grupos formam um puzzle válido de Sumdoku
        if (!isValidForPuzzle(grid) && !isValidForPuzzle(groups)) {
            return false;
        }

        // Verificar se existe uma unica solucao para o puzzle
        SumdokuSolver solver = new SumdokuSolver(grid, groups);
        int solutionCount = solver.howManySolutions(2); // 2 para verificar mais de uma solução
        return solutionCount == 1;
    }

    public static String cluesToString(SumdokuGrid grid, GridGroups groups) {
        StringBuilder result = new StringBuilder();
        int size = grid.size();

        for (int group = 1; group <= groups.numberOfGroups(); group++) {
            int groupSum = 0;

            // Encontrar os valores do grupo e calcular a soma
            for (int row = 1; row <= size; row++) {
                for (int column = 1; column <= size; column++) {
                    if (groups.groupOfSquare(row, column) == group) {
                        groupSum += grid.value(row, column);
                    }
                }
            }

            result.append(String.format("Group %d: Sum = %d\n", group, groupSum));
        }

        return result.toString();
    }

    // Procedimento readGrid
    public static SumdokuGrid readGrid(int size, Scanner scanner) {
        SumdokuGrid grid = new SumdokuGrid(size);

        for (int row = 1; row <= size; row++) {
            for (int column = 1; column <= size; column++) {
                System.out.printf("Enter value for cell (%d, %d): ", row, column);
                int value = scanner.nextInt();
                grid.fill(row, column, value);
            }
        }

        return grid;
    }

    // Procedimento readGroups
    public static GridGroups readGroups(int size, Scanner scanner) {
        System.out.print("Enter number of groups: ");
        int numberOfGroups = scanner.nextInt();
        GridGroups groups = new GridGroups(size, numberOfGroups);

        for (int group = 1; group <= numberOfGroups; group++) {
            System.out.printf("Enter number of cells in group %d: ", group);
            int cellsInGroup = scanner.nextInt();

            for (int i = 0; i < cellsInGroup; i++) {
                System.out.printf("Enter row and column for cell %d in group %d: ", i + 1, group);
                int row = scanner.nextInt();
                int column = scanner.nextInt();
                groups.addSquareToGroup(row, column, group);
            }
        }

        return groups;
    }

    // Funcao puzzleSolved
    public static boolean puzzleSolved(SumdokuGrid playedGrid, SumdokuGrid grid) {
        int size = grid.size();

        for (int row = 1; row <= size; row++) {
            for (int column = 1; column <= size; column++) {
                if (playedGrid.value(row, column) != grid.value(row, column)) {
                    return false;
                }
            }
        }

        return true;
    }

    // Procedimento play
    public static void play(SumdokuGrid grid, GridGroups groups, int maxAttempts, Scanner scanner) {
        SumdokuGrid playedGrid = new SumdokuGrid(grid.size());
        System.out.println("Bem vindo ao jogo Sumdoku!");
        System.out.println(grid);

        int attempts = 0;
        while (attempts < maxAttempts) {
            System.out.printf("Attempt %d/%d\n", attempts + 1, maxAttempts);
            System.out.print("Enter row, column, and value (e.g., 1 2 3): ");
            int row = scanner.nextInt();
            int column = scanner.nextInt();
            int value = scanner.nextInt();

            playedGrid.fill(row, column, value);

            if (puzzleSolved(playedGrid, grid)) {
                System.out.println("Congratulations! You solved the puzzle!");
                return;
            }

            System.out.println("Current grid:");
            System.out.println(playedGrid);

            attempts++;
        }

        System.out.println("Game over! You reached the maximum number of attempts.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter grid size (3-9): ");
        int size = scanner.nextInt();

        SumdokuGrid grid = readGrid(size, scanner);
        GridGroups groups = readGroups(size, scanner);

        System.out.println("Puzzle clues:");
        System.out.println(cluesToString(grid, groups));

        System.out.print("Introduza o numero maximo de tentativas");
        int maxAttempts = scanner.nextInt();

        play(grid, groups, maxAttempts, scanner);
    }
}
