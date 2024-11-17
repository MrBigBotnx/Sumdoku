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

    // Funcao isValidPuzzle
    public static boolean isValidForPuzzle(SumdokuGrid grid, GridGroups groups) {
        if (grid == null || groups == null) return false;

        int size = grid.size();

        // Vamos aqui validar a grid
        for (int i = 0; i < size * size; i++) {
           int row = rowOfSquare(i, size) + 1;
           int column = columnOfSquare(i, size) + 1;
           int value = grid.value(row, column);

           if (value < 1 || value > size) {
               return false;
           }
        }

        // Aqui validamos o grupo
        int numberOfGroups = groups.numberOfGroups();
        for (int group = 1; group <= numberOfGroups; group++) {
            int groupSum = 0;
            boolean hasSquares = false;

            for (int row = 1; row <= size; row++) {
                for (int column = 1; column <= size; column++) {
                    if (groups.groupOfSquare(row, column) == group) {
                        hasSquares = true;
                        groupSum += grid.value(row, column);
                    }
                }
            }

            // Devemos tambem verificar se o grupo contem pelo menos uma casa e a soma deve ser valida
            if (!hasSquares || groupSum <= 0) {
                return false;
            }
        }
        return true;
    }
}
