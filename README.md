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

Markov processes are widely used in Computer Science and in analyzing different forms of data. Part II of this assignment offers an occasionally amusing look at a *generative model* for creating realistic looking text in a data-driven way using a Markov Process. Part 1 (this project) begins building infrastructure for analyzing text in an object-oriented way by building out a `WordGram` class. 

Generative models of the sort you will build are of great interest to researchers in artificial intelligence and machine learning generally, and especially those in the field of *natural language processing* (the use of algorithmic and statistical AI/ML techniques on human language). The most recent and power such text-generation model via statistical machine learning program is the [OpenAI GPT-3](https://www.theverge.com/2020/6/11/21287966/openai-commercial-product-text-generation-gpt-3-api-customers).

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
You're given an outline of `WordGram.java` with stub (unimplemented) methods and a stub constructor. Your task will be to implement these methods in `WordGram` according to the specifications detailed below. In particular, you should implement the following methods and constructor:
    - The constructor `WordGram(String[] words, int index, int size)`
    - `toString()`
    - `hashCode()`
    - `equals(Object other)`
    - `length()`
    - `shiftAdd(String last)`

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

You are also provided with JUnit tests (described later) that you can use to test your implementation. Expand the following sections for details on each of these methods.

<details>
<summary>Implement the Constructor</summary>

The constructor for WordGram `public WordGram(String[] source, int start, int size)`
should store `size` strings from the array `source`, starting at index `start` (of `source`) into a private `String` array instance variable `myWords` of the `WordGram` class. The array `myWords` should contain exactly `size` strings. There are three instance variables in `WordGram`:
```
private String[] myWords;
private String myToString;
private int myHash;
```

_**You must give each of these instance variables a value in the constructor.**_ Instance variable values given to `myToString` and `myHash`  will change when you implement the methods `.toString()` and `.hashCode()`, respectively. **In the code you clone, these are given values, you'll need to add Strings to `myWords`.**

The constructor of a `WordGram` takes an array of strings as a parameter and copies `size` of these, starting at index `start`, into the instance variable `myWords`. For example, suppose parameter `words` is the array below, with "this" at index 0.

| | | | | | | |
| --- | --- | --- | --- | --- | --- | --- |
| "this" | "is" | "a" | "test" |"of" |"the" |"code" |
| | | | | | |

The call `new WordGram(words,3,4)` should create this array `myWords` since the starting index is the second parameter, 3, and the size is the third parameter, 4.

| | | | |
| --- | --- | --- | --- |
| "test" | "of" | "the" | "code"|
| | | | |
</details>


<details>
<summary>Implement and override toString()</summary>

The `toString()` method should return a printable `String` representing all the strings stored in the `WordGram`. This should be a single `String` storing each of the values in instance variable `myWords` separated by a space. You can do this using the static [`String.join`](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#join-java.lang.CharSequence-java.lang.CharSequence...-) method with a first parameter of a single-space: `" "` and the second parameter the instance variable `myWords`. 

_**Don't recompute this `String` each time `toString` is called -- store the String in instance variable `myToString`. For full credit your code**_ must only call `String.join` the first time `.toString()` is called and will then simply return the value stored in `myToString` on subsequent calls. You can compare the initial value of `myToString` to the default value `null` to see if you need to assign a value to it. Once `myString` is **not* null, you'll be able to use it rather than recomputing it. Note that `WordGram` objects are immutable, so we don’t need to worry about updating `myString` later.

</details>


<details>
<summary>Implement and override hashCode()</summary>

The `hashCode()` method should return an `int` value based on all the strings in instance variable `myWords`. A simple and efficient way to calculate a hash value is to call `this.toString()` and to use the hash-value of the resultant String created and returned by `this.toString() `_**-- you should use this method in calculating hash values for `WordGram` objects.**_

_**Don't recompute the hash value each time `hashCode` is called --**_ Since `myHash` is set to a default value of 0 in the constructor, then you need to call `.toString().hashCode()` only if `myHash` is 0. Store this value in `myHash`, then just return `myHash` in subsequent calls if it isn't zero. As before, `WordGram` objects are immutable, so we don’t need to worry about updating `myHash` later. 

</details>


### Implementing `equals`, `length`, and `shiftAdd`

Next you should implement the remaining three methods of the `WordGram` class. Expand each section below for details.

<details>
<summary>Implement and override equals()</summary>

The `equals()` method should return `true` when the parameter passed is a `WordGram` object with _**the same strings in the same order**_ as this object. Your code should test the object parameter with the `instanceof` operator to see if the parameter is a `WordGram.` You're given code that makes this test and returns false when the parameter is not a `WordGram` object.

If the parameter `other` is a `WordGram` object, it can be cast to a `WordGram`, e.g., like this:
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

If this `WordGram` has k entries then the `shiftAdd()` method should create and return a _**new**_ `WordGram` object, also with k entries, whose first k-1 entries are the same as the last k-1 entries of this `WordGram`, and whose last entry is the parameter `last`. Recall that `WordGram` objects are immutable and cannot be modified once created - **you must create a new WordGram object** in this method to return back to the user.

For example, if `WordGram w` is 
| | | |
| --- | --- | --- |
| "apple" | "pear" | "cherry" |
| | | | 

then the method call `w.shiftAdd("lemon")` should return a new `WordGram` {"pear", "cherry", "lemon"}. Note that this new `WordGram` will not equal w.

The call `w.shiftAdd(string)` is meant to be an analog of the call `s.substring(1).concat(char)` for a String object s.  

Note: To implement shiftAdd you'll need to create a new `WordGram` object, say referenced by a local variable wg. You'll be able to assign values to the instance variables of this wg object directly since the shiftAdd method is in the `WordGram` class.

</details>

After finishing the `shiftAdd` method (and others above), running `SimpleWordGramDriver` should show the following output.
```
gram = Computer Science is fun, length = 4, hash = 52791914
gram = Science is fun sometimes, length = 4, hash = 1248130903
```


## JUnit Tests

To test your `WordGram` class, you’re given testing code. This code tests individual methods in your class, such tests are called unit tests, and you'll need to use the standard JUnit unit-testing library with the WordgramTest.java file to test your implementation. In this assignment you'll be using JUnit 5; the requisite files `jar` files should be included along with this project in a folder called `lib`. 

There are several tests in WordGramTest.java: `testEqualsTrue()`, `testEqualsFalse()` which check the correctness of .equals; `testHashEquals()` which checks the consistency of equals and hashing; `testHashDensity()` which checks the “performance” of the .hashCode method, and `testShift()` which checks, to a limited extent, the correctness of shiftAdd.


## Benchmarking and Analysis

<!-- WE SHOULD HAVE ANALYSIS DOCS! TODO: Make Google Docs with -->
Answer the questions below once you have finished programming. You should write your answers in some text editor and then save into a PDF when you’re finished.  To change the size of the `WordGram` you're benchmarking/testing, you should change the WordGramBenchmark static instance variable WSIZE.  ** You'll submit a PDF to Gradescope in a separate P2: Analysis assignment.**

**Q1: Running Benchmarks**

Run WordGramBenchmark for wordgrams of size 2-10 and record
the number of WordGram values/objects that occur more than
once as reported by the runs. For example, with WSIZE = 2,
which generates 2-grams, the output of benchmark and benchmarkShift
each indicates that the total # wordgrams generated is 177,634
and that the # unique wordgrams is 117,181

This means there are 177,634 - 117,181 = 60,453 WordGram values that
occur more than once. Find these same numbers/values for other orders
of k and complete the table below for different k-grams (in other words different 
values of `WSIZE`).

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

**Q2: Explaining Benchmarks**

Explain in your own words the conceptual differences between 
the benchmark and benchmarkShift methods. 
Answer these questions: 

(1) Explain wny the results of these methods should be the same in 
terms of changes made to the `HashSet` parameter passed to each method.

(2) What are the conceptual differences between the two
benchmarking methods.

**Q3: Biased Algorithms**

Read the article written by Duke alum Cade Metz: Metz, C. (2021). _Who is making sure the AI machines aren’t racist_. The New York Times, 15.
1. https://www.nytimes.com/2021/03/15/technology/artificial-intelligence-google-bias.html
2. (mirrored at Duke) https://courses.cs.duke.edu/compsci201/current/notes/ai-racist-nytimes.pdf

Based on this article and your own thoughts, do you think algorithms can be biased? Explain.


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

