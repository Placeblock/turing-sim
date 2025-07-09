# Turing Machine Simulator

<b>DISCLAIMER:</b> We do not like Java Swing. This is for University purposes only!

A graphical Java application for designing, configuring, and simulating Turing machines. This educational tool provides an intuitive interface for creating transition tables, visualizing tape operations, and stepping through machine execution.

## Features

- **Interactive State Register**: Create and modify states with transitions using a table-based editor
- **Visual Tape Display**: Real-time visualization of the tape with highlighted head position
- **Machine Controls**: Step through execution manually or run continuously with adjustable speed
- **Configuration Management**: Save and load machine configurations and initial tape states
- **State Management**: Define initial states, final states, and transition functions
- **Alphabet Customization**: Configure tape alphabet and blank symbols

## Dependencies

- **Lombok**: Reduces boilerplate code by automatically generating getters, setters, constructors, and other common methods
- **FlatLaf**: Modern look-and-feel library that provides a clean, flat design theme for the Swing GUI

## Getting Started

1. Launch the application
2. Either load an existing configuration file or start with example data
3. Optionally load an initial tape state from a file
4. Design your Turing machine using the state register table
5. Use the control panel to execute and observe your machine

## Usage

- **Right-click** on states, symbols, or transitions for context menus with additional options
- **Step button**: Execute one transition at a time
- **Play/Stop**: Run the machine continuously
- **Speed slider**: Adjust execution speed during continuous runs
- **Reset**: Return to initial configuration

The application validates your machine configuration and provides error messages for incomplete or invalid transitions.

## File Formats

- **Configuration files**: Complete machine definitions including states, transitions, and alphabet
- **Tape files**: Initial tape content as plain text
