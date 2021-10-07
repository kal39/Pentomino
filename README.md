# Pentomino Puzzle Solver

Uploaded on `2021/10/7`

## Authors

Qi Cui, Niklas Druba, Kai Kitagawa-Jones, Guillerme Pereira Sequeira, Irena Shaleva, Fei Yu, Anton Zegelaar

## Purpose of Project

Develop an efficient algorithm to solve the pentomino puzzle.

## Usage Instructions

Run the `main` method in `puzzle_solver/gui/Gui.java` (for example, through `VSCode` or with `javac puzzle_solver/gui/Gui.java; java puzzle_solver/gui/Gui`) to start the GUI. The board size and the pentominos to use can be changed inside the GUI.

There are three algorithms available: `Basic` (based on the code provided by the university), `Branching` (a branching algorithm with pruning), and `Algorithm X` (an method that uses Algorithm X). The algorithms can also be selected from the GUI.

In order to run the algorithms directly without a GUI:

-  `Basic`: run the `main` method in `puzzle_solver/basic/Search.java`
-  `Branching`: run the `main` method in `puzzle_solver/branching/BranchingAlgorithmPruning.java`
-  `Algorithm X`: run the `main` method in `puzzle_solver/algorithm_x/AlgoXSolver.java`

To change the board width, board height, and allowed pentominos, change the values of `WIDTH`, `HEIGHT`, and `PENTOMINOS` respectively.

All algorithms support using a pentomino more than once. In order to do this, enter the letter representing the pentomino multiple times.

The method which we used to measure the performance of each algorithm can be found at `puzzle_solver/testing/MeasurePerformance.java`

