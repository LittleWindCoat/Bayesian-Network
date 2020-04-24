CSC242 PROJECT 3 Uncertain Inference LWC

We used the provided classes for Project 3 to implement our algorithms. 

To use the command-line: Place proj3 folder on desktop, open command prompt and type in the following command using aima-alarm.xml as an example:

cd desktop
cd proj3
cd bin
java bn/inference/MyBNInferencer aima-alarm.xml B J true M true
{false=0.7320574162679426, true=0.2679425837320574}

More examples:

Using aima-wet-grass.xml, type in:
java bn/inference/MyBNInferencer aima-wet-grass.xml R S true
{true=0.30067046597385183, false=0.6993295340261482}

Using dog-problem.xml, type in:
java bn/inference/MyBNInferencer dog-problem.xml light-on family-out true
{true=0.5955941255006676, false=0.40440587449933246}

Using alarm.bif, type in:
java bn/inference/MyBNInferencer alarm.bif HISTORY LVFATLURE TRUE
{TRUE=0.9053497942386831, FALSE=0.09465020576131687}

Enumeration, Rejection Sampling and Likelihood Weighting all use MyBNInferencer (samples do not need to be specified, RS and LW were given default samples).
Gibbs Sampling uses the GibbsInferencer. 

java bn/inference/GibbsInferencer 1000 aima-alarm.xml B J true M true
{true=0.284, false=0.716}

In other cases, example.xml must match a file from examples and all the files should be in the proj3 directory. 

The above output was produced with the enumeration algorithm. To run the project, you can also run directly from Eclipse. The main method is included in the MyBNInferencer.java. To test different algorithm, you can go into the MyBNInferencer.java and comment out the algorithm you need. An sample output using the aima-wet-grass.xml:
You can get the following output by running configuration. This means you need to type in "aima-wet-grass.xml R S true" in the arguement. 

Enumeration output: {true=0.3, false=o,7}

Rejection Sampling output: {false=0.6966626538255751, true=0.3033373461744248}

Likelihood Sampling output: {true=0.2968123305895949, false=0.703187669410405}

Gibbs Sampling output: {true=0.288, false=0.712}