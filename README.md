# P2: Markov Part I, Spring 2022

## Outline
- [Background (Not necessary to complete assignment)](#background-not-necessary-to-complete-assignment)
- [High-Level TODOs](#high-level-todos)
- [Git](#starter-code-and-git)
- [Pushing to Git](#pushing-to-git)
- [Running SimpleWordGramDriver](#running-simplewordgramdriver)
- [Overview of WordGram](#overview-of-wordgram)
- [Implementing WordGram](#implementing-wordgram)
- [WordGram Constructors and Methods](#wordgram-constructors-and-methods)
    - [(1) Implement the Constructor](#1-implement-the-constructor)
    - [(2) Implement and override method toString()](#2-implement-and-override-method-tostring)
    - [(3) Implement and override method hashCode()](#3-implement-and-override-method-hashcode)
    - [(4) Implement and override method equals()](#4-implement-and-override-method-equals)
    - [(5) Implement the method length()](#5-implement-the-method-length)
    - [(6) Implement the method shiftAdd()](#6-implement-the-method-shiftadd)
- [JUnit Tests Explained](#junit-tests-explained)
- [Submitting](#submitting)
- [Reflect Form](#reflect-form)
- [Analysis](#analysis)
- [Grading](#grading)


## Background (Not necessary to complete assignment)
<details>
<summary>Click to Expand</summary>

Markov processes are widely used in Computer Science and in analyzing different forms of data. Part II of this assignment offers an occasionally amusing look at text (it's more fun than counting words) by using a Markov process to generate random text based on a training text. 

When run in reverse (we won't do that in this assignment), it's possible to identify the source of an unknown text based on frequency of letters and words. This process can be used to identify SPAM or to ascertain if Bacon wrote Romeo and Juliet.

The most recent text-generation via statistical machine learning program is the [OpenAI GPT-3](https://www.theverge.com/2020/6/11/21287966/openai-commercial-product-text-generation-gpt-3-api-customers), you can read more using Google, but that's a start.  If you're on Facebook, you can use the what-would-i-say FB (or Android) app, described here http://what-would-i-say.com/about.html as "Technically speaking, it trains a **Markov Bot** based on mixture model of **bigram and unigram probabilities** derived from your past post history."

You can also read about the so-called "Infinite Monkey Theorem" via its [Wikipedia entry](https://en.wikipedia.org/wiki/Infinite_monkey_theorem_in_popular_culture). 
<div align="center">
<img width="300 height="200" src="p2-figures/P2-monkey.png">
</div>


This assignment has its roots in several places: a story named _Inflexible Logic_ now found in pages 91-98 from [_Fantasia Mathematica (Google Books)_](http://books.google.com/books?id=9Xw8tMEmXncC&printsec=frontcover&pritnsec=frontcover#PPA91,M1) and reprinted from a 1940 New Yorker story called by Russell Maloney. 
The true mathematical roots are from a 1948 monolog by Claude Shannon, [A Mathematical Theory of Communication](https://docs.google.com/viewer?a=v&pid=sites&srcid=ZGVmYXVsdGRvbWFpbnxtaWNyb3JlYWRpbmcxMmZhbGx8Z3g6MThkYzkwNzcyY2U5M2U5Ng) which discusses in detail the mathematics and intuition behind this assignment. This assignment has its roots in a Nifty Assignment designed by Joe Zachary from U. Utah, assignments from Princeton designed by Kevin Wayne and others, and the work done at Duke starting with Owen Astrachan and continuing with Jeff Forbes, Salman Azhar, and the UTAs from Compsci 201.
</details>


## High-level TODOs
<details>
<summary>Click to Expand</summary>

Run `SimpleWordGramDriver` to see that it runs, the output will be wrong, but you'll see that it runs as a Java program. Add your name as a comment at the top of the `WordGram.java` file, then push your changes to Git to confirm that Git works. You might need to include JUnit as part of the project accessible libraries to run SimpleWordGramDriver, see JUnit section for more.

Implement the constructor and `.toString` method for class `WordGram`. Run the driver program. Implement the methods `.hashCode`, `.equals`, and `.shiftAdd` in `WordGram` Test using `SimpleWordGramDriver` *and* using the JUnit tests in `WordGramTest.` If all tests pass and the SimpleWordGramDriver output matches expected output, then answer the questions in the analysis section by running the `WordGramBenchmark program` several times as asked for. Submit program on Gradescope via GitLab, and submit analysis also on Gradescope.
</details>


## Starter Code and Git
<details>
<summary>Click to Expand</summary>

Fork, clone, and import the cloned project from the file system. Use this URL from the course GitLab site: https://coursework.cs.duke.edu/201fall21/P2-Markov-Part-1. **Be sure to fork first (see screen shot).** 
<div align="center">
<img width="300" height="50" src="p2-figures/P2-fork-first.png">
</div>

**After you have forked**, Clone the SSH URL as seen here: 
<div align="center">
<img width="300" height="200" src="p2-figures/P2-clone-ssh.png">
</div>
Then move (cd) into your IntelliJ workspace - the folder that you store your projects in (for most of you, this will be ~/IdeaProjects/ or whichever folder you're storing your projects in) - and verify that you're in the right location with pwd. You'll type git clone SSH_URL_COPIED to clone your repo onto your local computer. Then open from IntelliJ.

### Pushing to Git

When you make a series of changes you want to 'save', you'll push those changes to your GitHub repository. You should do this after major changes, certainly every hour or so of coding. You'll need to use the standard Git sequence to commit and push to GitHub:

```
git add .
git commit -m 'a short description of your commit here'
git push
```

</details>

More detailed explanations on using Git can be found via the Using Git document [here][Using IntelliJ, Gradescope, and Git].

## Running `SimpleWordGramDriver`
<details>
<summary>Click to Expand</summary>

Be sure you can run the SimpleWordGramDriver. The output should be what's shown below: 
```
gram = null, length = 0, hash = 0
gram = null, length = 0, hash = 0
```
**However, you may see errors generated from the WordGramTest program** because JUnit libraries are missing. If this happens, just right click on one of the red  JUnit pieces of code and add the JUnit 5.x library to your project; IntelliJ will add the libraries to your project's path.
</details>


## Overview of WordGram
<details>
<summary>Click to Expand</summary>

Implement a class `WordGram` that represents a sequence of words or strings, just like a Java String represents a sequence of characters. As described below, implement the constructor and all stub-methods so you pass the provided tests and adhere to the design guidelines described below.

Just as the Java String class is an immutable sequence of characters, the `WordGram` class you implement will be an immutable sequence of strings. Immutable means that once a WordGram object has been created, it cannot be modified. You cannot change the contents of a `WordGram` object. However, you can create a new WordGram from an existing `WordGram`. Strings are also immutable.

The number of strings contained in a `WordGram` is sometimes called the *order* of the WordGram, and we sometimes call the `WordGram` an *order-k* WordGram, or a *k-gram* -- the term used in the Markov program you'll implement for Part II.  Some examples of order-3 `WordGram` objects include:
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

### Implementing `WordGram`
<details>
<summary>Click to Expand</summary>

You're given an implementation of `WordGram.java` with stub (unimplemented) methods and a stub constructor. See the screenshot from IntelliJ to the right that indicates the required methods, constructors, and the three `private` instance variables you'll create. In the `WordGram` class you get in the starter code these methods are not correct, as you can see if you run the JUnit tests in `WordGramTest`. You'll follow these general steps to provide a correct implementation.

1. Replace the stub/incomplete methods in `WordGram` with working versions. In particular, you should implement the following methods and constructor:
    - The constructor `WordGram(String[] words, int index, int size)`
    - `toString()`
    - `hashCode()`
    - `equals(Object other)`
    - `length()`
    - `shiftAdd(String last)`

For `hashCode`, `equals`, and `toString`, your implementations should conform to the specifications as given in the [documentation for `Object`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Object.html).

Test these methods using the JUnit tests in `WordGramTest`. 
</details>

### `WordGram` Constructors and Methods
<details>
<summary>Click to Expand</summary>

As you're implementing code for the first three methods: constructor, `.toString()`, and `.hashCode()`, you can use the program `SimpleWordGramDriver` to test what you've done, in addition to the JUnit tests described later in this document. The output of the program should be _different_ than when run after first cloning.

After implementing the constructor and two methods the first line of the output should be as shown below, _*after implementing `.shiftAdd`*_, you'll get the second line of output as well.

```
gram = Computer Science is fun, length = 4, hash = 52791914
gram = Science is fun sometimes, length = 4, hash = 1248130903
```

#### (1) Implement the Constructor
<details>
<summary>Click to Expand</summary>

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

#### (2) Implement and override method toString()
<details>
<summary>Click to Expand</summary>

The `toString()` method should return a printable `String` representing all the strings stored in the `WordGram`. This should be a single `String` storing each of the values in instance variable `myWords` separated by a space. You can do this using the static [`String.join`](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#join-java.lang.CharSequence-java.lang.CharSequence...-) method with a first parameter of a single-space: `" "` and the second parameter the instance variable `myWords`. 

_**Don't recompute this `String` each time `toString` is called -- store the String in instance variable `myToString`. For full credit your code**_ must only call `String.join` the first time `.toString()` is called and will then simply return the value stored in `myToString` on subsequent calls. You can compare the initial value of `myToString` to the default value `null` to see if you need to assign a value to it. Once `myString` is **not* null, you'll be able to use it rather than recomputing it. Note that `WordGram` objects are immutable, so we don’t need to worry about updating `myString` later.

</details>

#### (3) Implement and override method `hashCode()`
<details>
<summary>Click to Expand</summary>

The `hashCode()` method should return an `int` value based on all the strings in instance variable `myWords`. A simple and efficient way to calculate a hash value is to call `this.toString()` and to use the hash-value of the resultant String created and returned by `this.toString() `_**-- you should use this method in calculating hash values for `WordGram` objects.**_

_**Don't recompute the hash value each time `hashCode` is called --**_ Since `myHash` is set to a default value of 0 in the constructor, then you need to call `.toString().hashCode()` only if `myHash` is 0. Store this value in `myHash`, then just return `myHash` in subsequent calls if it isn't zero. As before, `WordGram` objects are immutable, so we don’t need to worry about updating `myHash` later. 

</details>

#### (4) Implement and override method `equals()`
<details>
<summary>Click to Expand</summary>

The `equals()` method should return `true` when the parameter passed is a `WordGram` object with _**the same strings in the same order**_ as this object. Your code should test the object parameter with the `instanceof` operator to see if the parameter is a `WordGram.` You're given code that makes this test and returns false when the parameter is not a `WordGram` object.

If the parameter `other` is a `WordGram` object, it can be cast to a `WordGram`, e.g., like this:
```
WordGram wg = (WordGram) other;
```

Then the strings in the array `myWords` of `wg` can be compared to this object's strings stored in `this.myWords`. Note that `WordGram` objects of different lengths cannot be equal, and your code should check this.

</details>

#### (5) Implement the method `length()`
<details>
<summary>Click to Expand</summary>

The `length()` method should return the order or size of the `WordGram` -- this is the number of words stored in the instance variable `myWords`.

</details>

#### (6) Implement the method `shiftAdd()`
<details>
<summary>Click to Expand</summary>

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

</details>

</details>

## JUnit Tests Explained
<details>
<summary>Click to Expand</summary>

To test your `WordGram` class, you’re given testing code. This code tests individual methods in your class, such tests are called unit tests, and you'll need to use the standard JUnit unit-testing library with the WordgramTest.java file to test your implementation. In this assignment you'll be using JUnit 5. You should be able to run tests by running the `WordGramTest` class just like a normal class. If you get an error, you may need to include the appropriate library. Intellij will take care of that for you if you right click on the red Test code and add JUnit 5 as seen in the image below.

<div align="center">
<img width="300" height="200" src="p2-figures/P2-AddJUnit.png">
</div>

See more on how to import JUnit5 here: https://www.jetbrains.com/help/idea/testing.html#add-testing-libraries

There are several tests in WordGramTest.java: `testEqualsTrue()`, `testEqualsFalse()` which check the correctness of .equals; `testHashEquals()` which checks the consistency of equals and hashing; `testHashDensity()` which checks the “performance” of the .hashCode method, and `testShift()` which checks, to some degree, the correctness of shiftAdd.

<div align="center">
<img width="600" height="200" src="p2-figures/P2-JUnitTests.png">
</div>

If the JUnit tests pass, you’ll get all green check marks as shown on the left below. Otherwise you’ll get an orange X mark — on the right below — and an indication of all the tests that fail. Choose one method to fix as needed and then go on to more tests. You'll see an explanation about why tests fail in the IntelliJ window to the right of the Test Results panel. You can use these test results to look at the tests and try to determine why your code fails.

</details>

## Submitting
<details>
<summary>Click to Expand</summary>

Push your code to Git. Do this often. You can use the autograder on Gradescope to test your code after you pass the JUnit tests. Note that you must complete the Reflect form, but you should NOT complete the reflect form until you're done with all the coding portion of the assignment. Since you may uncover bugs from the autograder, you should wait until you've completed debugging and coding before completing the reflect form.

</details>

### Reflect Form
<details>
<summary>Click to Expand</summary>

Complete the reflect form linked here: https://do-compsci.com/201fall21-p2-reflect 
</details>

## Analysis
<details>
<summary>Click to Expand</summary>

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

</details>


## Grading
<details>
<summary>Click to Expand</summary>

For this program grading will be:

| Points | Grading Criteria |
| ------ | ------ |
| 14 | correctness of WordGram constructor and methods and other results reported by autograder, e.g., API.|
| 6 |  Answers to analysis questions + Reflect |

</details>

<!-- ALL LINKS USED IN THIS DOCUMENT -->

[Using IntelliJ, Gradescope, and Git]:https://docs.google.com/document/d/1dlEwDwiIyEQFxXOHS_zY-Qojx4djl4p2Ud16qpeb7gY/edit?usp=sharing

