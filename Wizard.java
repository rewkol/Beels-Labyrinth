/*
 * Mitchell Golding 3552573
 * CS1083 Assignment 6
 * 5 April 2017
 *
 * Purpose: Stores Wizard data
 * Input: Random ranges of values for damage and whatnot
 * Output: Damage or dead
 */
package cs1083_assign6;

public class Wizard extends Entity{
    Wizard(int curfloor)
    {
        super(15+(curfloor*0.25));
    }
}
