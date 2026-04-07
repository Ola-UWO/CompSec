/*****************************************************
   CS 326 - Spring 2026 - Assignment #4

   Student's full name: Olalekan Abdulsalam
   Student's full name: Kyle Johnson
   Student's full name: Dasha Coates

 *****************************************************/

class RSA
{

    /* the numerical components of the RSA algorithm */
    int p, q, n, phiOfN, e, d;
    
    /* this constructor takes the value of p and q and initializes all of the
       instance variables of the RSA object with the following constraints on
       e and d:
       1. e and d are both prime numbers and relatively prime to phi(n), 
       2. 1 < e < phi(n) AND e <= d AND 0 < e * d <= Primes.SKY,  
       3. e * d = 1 + k * phi(n), for some positive integer k, and      
       4. the values of e and d are such that k is the smallest value that
          satisfies the previous condition.
       This constructor must print the instance variables with the 
       following format (only one line of output allowed):
             
       p=3 q=5 n=15 phi(n)=8 e=3 d=3 

       This line of output must end with a single newline character.
       
       If it is not possible to satisfy all of the conditions above, then this 
       constructor must throw an exception with the following message:
       "Cannot handle this case."
     */
    RSA(int p, int q) throws Exception
    {
        n = p * q;
        phiOfN = (p - 1) * (q - 1);
        e = -1;
        d = -1;
        int k = 1;
        int curr;

        for (int i = 2; i < phiOfN && e == -1; i ++) {
            int gcd = Primes.gcd(i, phiOfN);
            if (gcd == 1 && Primes.isPrime(i)) {
                e = i;
            }
        }

        if (e != -1) {
            while ( k < Primes.SKY && d == -1) {
                curr = 1 + k * phiOfN;

                if (curr % e == 0) {
                    int candidate = curr / e;

                    if (Primes.isPrime(candidate) && Primes.gcd(candidate, phiOfN) == 1) {
                        d = candidate;
                    }
                }
                k++;
            }
        }

        if (e != -1 && d != -1) {
            if (e <= d && (e * d) > 0 && (e * d) <= Primes.SKY) {
                System.out.println("p=" + p + " q=" + q +" n=" + n 
                + " phi(n)=" + phiOfN + " e=" + e + " d=" + d);
                return;
            }
        }
        throw new Exception("Cannot handle this case.");

    }// constructor

    /* compute and return:  a^e mod m 
       The implementation of this method MUST follow the pseudocode on
       Slide 9.6 as closely as possible with the addition of "modulo
       operations" wherever needed.
       Hint: Watch out for integer overflow situations. You must handle those 
       without any try/catch blocks and without any additional tests.
       This method may not send anything to standard output.
     */
    public static int modularExponent(int a, int e, int m)
    {
        if (e == 0) return 1;
        int y = 1;
        while (e > 1) {
            if (e % 2 == 0) {
                a = (a * a) % m;
                e = e / 2;
            } else {
                y = (y * a) % m;
                a = (a * a) % m;
                e = (e - 1) / 2;
            }
        }

        return (y * a) % m; 
    }// modularExponent method

    /* return the ciphertext produced by this RSA instance for the input 
       plaintext m
       This method may not send anything to standard output.
     */
    public int encrypt(int m)
    {
        int c = 0;
        if (m < n) {
            c = modularExponent(m, e, n);
        }
        return c; 
    }// encrypt method

    /* return the plaintext produced by this RSA instance for the input 
       ciphertext c
       This method may not send anything to standard output.
     */
    public int decrypt(int c)
    {
        int m = modularExponent(c, d, n);
        
        return m;
    }// decrypt method

}// RSA class
