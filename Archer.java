/*
 * Mitchell Golding 3552573
 * CS1083 Assignment 6
 * 5 April 2017
 *
 * Purpose: Stores Archer data
 * Input: Random ranges of values for damage and whatnot
 * Output: Damage or Stop, my arrow can only withstand a certain drawweight
 */
package cs1083_assign6;

public class Archer extends Entity{
    Archer(int curelwa)
    {
        super((curelwa*0.1)*10+7);
    }
}
