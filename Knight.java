/*
 * Mitchell Golding 3552573
 * CS1083 Assignment 6
 * 5 April 2017
 *
 * Purpose: Stores Entity data
 * Input: Random ranges of values for damage and whatnot
 * Output: Damage or dead
 */
package cs1083_assign6;

public class Knight extends Entity {
    Knight(int curlfoor)
    {
        super(10+(0.25*curlfoor));
    }
}
