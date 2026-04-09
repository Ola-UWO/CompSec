/*****************************************************
 * CS 326 - Spring 2026 - Assignment #4
 * 
 * Student's full name: Olalekan Abdulsalam
 * Student's full name: Kyle Johnson
 * Student's full name: Dasha Coates
 * 
 *****************************************************/

class RSA {

  /* the numerical components of the RSA algorithm */
  int p, q, n, phiOfN, e, d;

  /*
   * this constructor takes the value of p and q and initializes all of the
   * instance variables of the RSA object with the following constraints on
   * e and d:
   * 1. e and d are both prime numbers and relatively prime to phi(n),
   * 2. 1 < e < phi(n) AND e <= d AND 0 < e * d <= Primes.SKY,
   * 3. e * d = 1 + k * phi(n), for some positive integer k, and
   * 4. the values of e and d are such that k is the smallest value that
   * satisfies the previous condition.
   * This constructor must print the instance variables with the
   * following format (only one line of output allowed):
   * 
   * p=3 q=5 n=15 phi(n)=8 e=3 d=3
   * 
   * This line of output must end with a single newline character.
   * 
   * If it is not possible to satisfy all of the conditions above, then this
   * constructor must throw an exception with the following message:
   * "Cannot handle this case."
   */
  RSA(int p, int q) throws Exception {
    boolean canHandleCase = false;
    this.p = p;
    this.q = q;
    n = p * q;
    phiOfN = (p - 1) * (q - 1);
    for (int i = 2; i < phiOfN && !canHandleCase; i++) {
      if (Primes.isPrime(i) && Primes.gcd(i, phiOfN) == 1) {
          for (int k = 1; !canHandleCase; k++) {
              int numerator = 1 + k * phiOfN;
              if (numerator > Primes.SKY) break;
              if (numerator % i != 0) continue;
  
              int candidateD = numerator / i;
              if (Primes.isPrime(candidateD) &&
                  Primes.gcd(candidateD, phiOfN) == 1 &&
                  i <= candidateD) {
                  e = i;
                  d = candidateD;
                  canHandleCase = true;
              }
          }
      }
  }
    if (!canHandleCase) {
      throw new Exception("Cannot handle this case.");
    } else {
      System.out.printf("p=%d q=%d n=%d phi(n)=%d e=%d d=%d%n",
          p, q, n, phiOfN, e, d);
    }
  }// constructor

  /*
   * compute and return: a^e mod m
   * The implementation of this method MUST follow the pseudocode on
   * Slide 9.6 as closely as possible with the addition of "modulo
   * operations" wherever needed.
   * Hint: Watch out for integer overflow situations. You must handle those
   * without any try/catch blocks and without any additional tests.
   * This method may not send anything to standard output.
   */
  public static int modularExponent(int a, int e, int m) {
    if (e == 0) return 1;
    long base = a % m;
    long y = 1;
    while (e > 1) {
      if (e % 2 == 0) e /= 2;
      else {
        y = (y * base) % m;
        e = (e - 1) / 2;
      }
      base = (base * base) % m;
    }
    return (int)((base * y) % m);
  }// modularExponent method

  /*
   * return the ciphertext produced by this RSA instance for the input
   * plaintext m
   * This method may not send anything to standard output.
   */
  public int encrypt(int m) {
    return modularExponent(m, e, n);
  }// encrypt method

  /*
   * return the plaintext produced by this RSA instance for the input
   * ciphertext c
   * This method may not send anything to standard output.
   */
  public int decrypt(int c) {
    return modularExponent(c, d, n);
  }// decrypt method

}// RSA class
