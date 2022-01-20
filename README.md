# Project 2: Markov Part I, Spring 2022

This is the directions document for Project P2 Markov Part 1 in CompSci 201 at Duke University, Spring 2022. Please follow the directions carefully while you complete the project. Please refer to the directions at https://coursework.cs.duke.edu/201spring22/p2-markov-part-1/ rather than any forks or local copies in the event that any changes are made to the document.



## Outline
- [Background](#background)
- [Starter Code and Using Git](#starter-code-and-using-git)
- [Developing the `WordGram` Class](#developing-the-wordgram-class)
    - [What is a `WordGram`](#what-is-a-wordgram)
    - [Getting Started](#getting-started)
    - [Implementing `WordGram` Constructor, `toString`, and `hashCode`](#implementing-wordgram-constructor-tostring-and-hashcode)
    - [Implementing `equals`, `length`, and `shiftAdd`](#implementing-equals-length-and-shiftadd)
- [JUnit Tests](#junit-tests)
- [Benchmarking and Analysis](#benchmarking-and-analysis)
- [Submitting, Reflect, Grading](#submitting-reflect-grading)


## Background

Markov processes are widely used in Computer Science and in analyzing different forms of data. Part II of this assignment offers an occasionally amusing look at a *generative model* for creating realistic looking text in a data-driven way using a Markov Process. Part 1 (this project) begins building infrastructure for analyzing text in an object-oriented way by building out a [`WordGram` class](#what-is-a-wordgram). We will use the `WordGram`s in this project to perform some analysis on the text of several plays by William Shakespeare in the [Benchmarking and Analysis](#benchmarking-and-analysis) section after you have finished coding.

Generative models of the sort you will build in Markov Part 2, are of great interest to researchers in artificial intelligence and machine learning generally, and especially those in the field of *natural language processing* (the use of algorithmic and statistical AI/ML techniques on human language). The most recent and power such text-generation model via statistical machine learning program is the [OpenAI GPT-3](https://www.theverge.com/2020/6/11/21287966/openai-commercial-product-text-generation-gpt-3-api-customers).

<details>
<summary>Historical details of this assignment (optional)</summary>

This assignment has its roots in several places: a story named _Inflexible Logic_ now found in pages 91-98 from [_Fantasia Mathematica (Google Books)_](http://books.google.com/books?id=9Xw8tMEmXncC&printsec=frontcover&pritnsec=frontcover#PPA91,M1) and reprinted from a 1940 New Yorker story called by Russell Maloney. 
The true mathematical roots are from a 1948 monolog by Claude Shannon, [A Mathematical Theory of Communication](https://docs.google.com/viewer?a=v&pid=sites&srcid=ZGVmYXVsdGRvbWFpbnxtaWNyb3JlYWRpbmcxMmZhbGx8Z3g6MThkYzkwNzcyY2U5M2U5Ng) which discusses in detail the mathematics and intuition behind this assignment. This assignment has its roots in a Nifty Assignment designed by Joe Zachary from U. Utah, assignments from Princeton designed by Kevin Wayne and others, and the work done at Duke starting with Owen Astrachan and continuing with Jeff Forbes, Salman Azhar, and the UTAs from Compsci 201.
</details>


## Starter Code and Using Git
You must have installed all software (Java, Git, VS Code) before you can complete the project.You can find the [directions for installation here](https://coursework.cs.duke.edu/201-public-documentation/resources-201/-/blob/main/installingSoftware.md).

We'll be using Git and the installation of GitLab at [coursework.cs.duke.edu](https://coursework.cs.duke.edu). All code for classwork will be kept here. Git is software used for version control, and GitLab is an online repository to store code in the cloud using Git.

**[This document details the workflow](https://coursework.cs.duke.edu/201-public-documentation/resources-201/-/blob/main/projectWorkflow.md) for downloading the starter code for the project, updating your code on coursework using Git, and ultimately submitting to Gradescope for autograding.** We recommend that you read and follow the directions carefully while working on a project! While coding, we recommend that you periodically (perhaps when completing a method or small section) push your changes as explained in Section 5.


## Developing the `WordGram` Class

### What is a `WordGram`
You will implement a class callsed `WordGram` that represents a sequence of words (represented as strings), just like a Java String represents a sequence of characters. Just as the Java String class is an immutable sequence of characters, the `WordGram` class you implement will be an immutable sequence of strings. Immutable means that once a WordGram object has been created, it cannot be modified. You cannot change the contents of a `WordGram` object. However, you can create a new WordGram from an existing `WordGram`.

The number of strings contained in a `WordGram` is sometimes called the *order* of the WordGram, and we sometimes call the `WordGram` an *order-k* WordGram, or a *k-gram* -- the term used in the Markov program you'll implement for Part 2.  Some examples of order-3 `WordGram` objects include:
| | | |
| --- | --- | --- |
| "cat" | "sleeping" | "nearby" |
| | | |

and 
| | | |
| --- | --- | --- |
| "chocolate" | "doughnuts" | "explode" |
| | | |

You'll construct a `WordGram` object by passing as constructor arguments: an array, a starting index, and the size (or order) of the `WordGram.` You'll **store the strings in an array instance variable** by copying them from the array passed to the constructor.


### Getting Started
You're given an outline of `WordGram.java` with stub (unimplemented) methods and a stub constructor. Your task will be to implement these methods in `WordGram` according to the specifications detailed below. In particular, you should implement the following

- The constructor `WordGram(String[] words, int index, int size)`
- `toString()`
- `hashCode()`
- `equals(Object other)`
- `length()`
- `shiftAdd(String last)`

There is also a `wordAt()` method, but it is already completed, you do not need to edit this method.

For `hashCode`, `equals`, and `toString`, your implementations should conform to the specifications as given in the [documentation for `Object`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html). As you develop, you will test your implementation using the *JUnit* tests in `WordGramTest`. 

Before you start coding, be sure you can run the `SimpleWordGramDriver`. The output (before you have done anything to `WordGram` should be what's shown below.
```
gram = null, length = 0, hash = 0
gram = null, length = 0, hash = 0
```


### Implementing `WordGram` Constructor, `toString`, and `hashCode`

The first three methods you should implement are the constructor, `.toString()`, and `.hashCode()`. Once you have completed these, you can again run program `SimpleWordGramDriver`; you should get different output - in particular the first line should now be
```
gram = Computer Science is fun, length = 4, hash = 52791914
```

You are also provided with [JUnit tests](#junit-tests) that you can use to test your implementation. Expand the following sections for details on each of these methods.

<details>
<summary>Implement the Constructor</summary>

There are three instance variables in `WordGram`:
```
private String[] myWords;
private String myToString;
private int myHash;
```

The constructor for WordGram `public WordGram(String[] source, int start, int size)`
should store `size` strings from the array `source`, starting at index `start` (of `source`) into the private `String` array instance variable `myWords` of the `WordGram` class. The array `myWords` should contain exactly `size` strings. 

For example, suppose parameter `words` is the array below, with "this" at index 0.

| | | | | | | |
| --- | --- | --- | --- | --- | --- | --- |
| "this" | "is" | "a" | "test" |"of" |"the" |"code" |
| | | | | | |

The call `new WordGram(words,3,4)` should create this array `myWords` since the starting index is the second parameter, 3, and the size is the third parameter, 4.

| | | | |
| --- | --- | --- | --- |
| "test" | "of" | "the" | "code"|
| | | | |

You do not need to change the default values assigned to the instance variables `myToString` and `myHash` in the constructor stub; these will change when you implement the methods `.toString()` and `.hashCode()`, respectively.
</details>


<details>
<summary>Implement and override toString()</summary>

The `toString()` method should return a printable `String` representing all the strings stored in the `WordGram` instance variable `myWords`, each separated by a single blank space (that is, `" "`).

Don't recompute this `String` each time `toString()` is called -- instead, store the String in instance variable `myToString`. For full credit your code must only call calculate `myToString` the first time `toString()` is called; it should simply return the value stored in `myToString` on subsequent calls (remember `WordGram` is immutable, so it can't change on subsequent calls). To determine whether a given call to `toString()` is the first, you can compare to the default constructor value of `myToString`.

</details>


<details>
<summary>Implement and override hashCode()</summary>

The `hashCode()` method should return an `int` value based on all the strings in instance variable `myWords`. See the [Java API documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#hashCode()) for the design constraints to which a `hashCode()` method should conform. 

You will implement `.equals()` later, but we will count two `WordGram` objects as equal if their `myWords` instance variables contain the same String values in the same order. In addition, note that the Java String class already has a good [`.hashCode()` method](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html#hashCode()) we can leverage. Use the `.hashCode()` of the String returned by `this.toString()` to implement this method.

Don't recompute the hash value each time `.hashCode()` is called. Similar to `.toString()`, only compute it the first time `.hashCode()` is called, and store the result in the `myHash` instance variable (again noting that it cannot change later since `WordGram` objects are immutable). On subsequent calls, simply return `myHash`. Again you can check whether this is the first call by comparing to the default `myHash` value in the Constructor.
</details>


### Implementing `equals`, `length`, and `shiftAdd`

Next you should implement the remaining three methods of the `WordGram` class. Expand each section below for details.

<details>
<summary>Implement and override equals()</summary>

The `equals()` method should return `true` when the parameter passed is a `WordGram` object with _**the same strings in the same order**_ as this object. In general, the [Java API specification of `.equals()`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#equals(java.lang.Object) takes an `Object` type as input. The starter code provided uses the `instanceof` operator to see if the argument is indeed a `WordGram` and returns `false` if not.

If the parameter `other` is indeed a `WordGram` object then it can and should be cast to a `WordGram`, e.g., like this (you will need to add this to the starter code):
```
WordGram wg = (WordGram) other;
```

Then the strings in the array `myWords` of `wg` can be compared to this object's strings stored in `this.myWords`. Note that `WordGram` objects of different lengths cannot be equal, and your code should check this.

</details>


<details>
<summary>Implement length()</summary>

The `length()` method should return the order or size of the `WordGram` -- this is the number of words stored in the instance variable `myWords`.
</details>


<details>
<summary>Implement shiftAdd()</summary>

If this `WordGram` has k entries then the `shiftAdd()` method should create and return a _**new**_ `WordGram` object, also with k entries, whose *first* k-1 entries are the same as the *last* k-1 entries of this `WordGram`, and whose last entry is the parameter `last`. Recall that `WordGram` objects are immutable and cannot be modified once created - **this method must create a new WordGram object** and copy values correctly to return back to the user.

For example, if `WordGram w` is 
| | | |
| --- | --- | --- |
| "apple" | "pear" | "cherry" |
| | | | 

then the method call `w.shiftAdd("lemon")` should return a new `WordGram` containing {"pear", "cherry", "lemon"}. Note that this new `WordGram` will not equal w.

Note: To implement shiftAdd you'll need to create a new `WordGram` object. The code in the method will still be able to assign values to the private instance variables of that object directly since the `shiftAdd()` method is in the `WordGram` class.

</details>

After finishing the `shiftAdd` method (and others above), running `SimpleWordGramDriver` should show the following output.
```
gram = Computer Science is fun, length = 4, hash = 52791914
gram = Science is fun sometimes, length = 4, hash = 1248130903
```


## JUnit Tests

To test your `WordGram` class, you’re given testing code. This code tests individual methods in your class; such tests are called **unit tests**. We will talk more about these later in the course, but the basic idea is straightforward: A unit test specifies a given input and asserts an expected outcome of running a method, then runs your code to confirm that the expected outcome occurs. We provide these tests for you to **use** in this assignment, but you will not need to design any tests.

We use a major Java library called [**JUnit**](https://junit.org/junit5/) (specifically version 5) for creating and running these unit tests. It is not part of the standard Java API, so we have supplied the requisite files `JAR` files (Java ARchive files) along with this project in a folder called `lib` (you don't need to do anything with this).  

We have included the tests you will use in the `WordgramTest.java` file. You should be able to run tests by running `WordGramTest.java` just like any other class with a `main` method. There are several tests in WordGramTest.java: `testEqualsTrue()`, `testEqualsFalse()` which check the correctness of `equals()`; `testHashEquals()` which checks the consistency of equals and hashing; `testHashDensity()` which checks the “performance” of the `hashCode()` method, and `testShift()` which checks, to a limited extent, the correctness of `shiftAdd()`. 

The benefit of supplying these *local* (on your own machine) tests is twofold: (1) to introduce you to using unit tests yourself to locate bugs in the code, and (2) to allow you to catch bugs quickly without needing to rely on the (somewhat slower) Gradescope autograder until you are reasonably confident in your code.


## Benchmarking and Analysis

Complete this after you finish programming; the benchmarking and analysis will require you to run code and read/reason about code. You should write your answers in some text editor and then save into a PDF when you’re finished.  To change the size of the `WordGram` you're benchmarking/testing, you should change the value of the `WordGramBenchmark` static instance variable `WSIZE`.  ** You'll submit a PDF to Gradescope in a separate P2: Analysis assignment.**

`WordGramBenchmark.java` has a `main` method that analyzes several plays by William Shakespeare (the plain text of which are included in the `data/shakes/` folder). It does so using two different methods: `Benchmark` and `BenchmarkShift` on the text of each play, one at a time. These methods both read a text file, create `WordGram`s from the text, add the `WordGram`s they create to a `Set` of `WordGram`s (see the parameters), and return the total number of `WordGram`s created. These methods are two different ways of performing the same analysis; if you run the `main` method of `WordGramBenchmark` you should note that you get the same results printed twice. 

Running `WordGramBenchmark` with `WSIZE = 2` should, for example, print something like the following:

```
benchmark time: 0.157, size = 117181
total # wgs = 177634

benchmarkShift time: 0.157, size = 117181
total # wgs = 177634
```

**The timings will be different on your machine and accross different runs.** The rest of the information should be the same, and indicates  that after running `Benchmark` or `BenchmarkShift` respectively, 177,634 total `WordGram`s were created, and after adding which one of those to a `Set`, the resulting size of the `Set` is 117,181.

**Answer the following questions about `WordGramBenchmark` for the analysis.**

1. The static `benchmark` and `benchmarkShift` methods both generate the same result. How are they different? Specifically, answer the following about their differences: (a) Which method reads the entire file before creating any `WordGram`s and which creates `WordGram`s while reading the file? (b) Which method creates `WordGram`s by explicitly calling the Constructor every time and which one uses another method to implicitly create new `WordGram`? 
2. Given that 177,634 total `WordGram`s were created with `WSIZE = 2` but there are only 117,181 values in the `Set` when we add all of these `WordGram`s, how many duplicate `WordGram`s were created?
3.  Find the number of duplicate `WordGram`s created by running `WordGramBenchmark` with values of `WSIZE` ranging from 2 to 10. That is, fill out the chart below.

| WSIZE | # duplicates|
| --- | --- |
|  2  |   60,453
|  3  |   10,756
|  4  |
|  5  |
|  6  |
|  7  | 
|  8  |
|  9  |
|  10 |

4. Given what you observe in the table you filled out, Are the number of duplicates increasing or decreasing as a function of `WSIZE`? 
5. Recall that `WSIZE` is the number of words in a `WordGram` and the `WordGramBenchmark` is creating `WordGram`s of that size/order by scanning the texts of plays. How would you explain the trend you observe that is, why is the number of duplicate `WordGram`s changing in the way you observe as a function of `WSIZE`?
6. `WordGram`s like this are a useful way of breaking down a text into its constituent parts for subsequent analysis in a field called *natural language processing*, a subfield of artificial intelligence (or AI) and machine learning (or ML) which are parts of computer science that develop data-driven algorithms and applications. Read this article written by Duke alum Cade Metz about the people behind these technologies: Metz, C. (2021). _Who is making sure the AI machines aren’t racist_. The New York Times, 15.
    - https://www.nytimes.com/2021/03/15/technology/artificial-intelligence-google-bias.html
    - (mirrored at Duke) https://courses.cs.duke.edu/compsci201/current/notes/ai-racist-nytimes.pdf

Based on this article and your own thoughts, do you think algorithms can be biased? To what extent do you think it matters who develops algorithms? This question does not have a right/wrong answer, we are looking for a thoughtful reflection of one or two paragraphs.


## Submitting, Reflect, Grading

Push your code to Git. Do this often. Once you have run and tested your completed program locally:

1. Submit your code on gradescope to the autograder 
2. Submit a PDF to Gradescope in the separate P2: Analysis assignment.
3. Complete the reflect form linked here: TODO: ADD REFLECT FORM


For this project, grading will be:

| Points | Grading Criteria |
| ------ | ------ |
| 14 | correctness of WordGram constructor and methods and other results reported by autograder, e.g., API.|
| 6 |  Answers to analysis questions + Reflect |

<!-- ALL LINKS USED IN THIS DOCUMENT -->

[Using IntelliJ, Gradescope, and Git]:https://docs.google.com/document/d/1dlEwDwiIyEQFxXOHS_zY-Qojx4djl4p2Ud16qpeb7gY/edit?usp=sharing

