/*
 * Mitchell Golding 3552573
 * CS1083 Assignment 6
 * 5 April 2017
 *
 * Purpose: Stores trap data
 * Input: Random ranges of values for damage and whatnot
 * Output: Damage or disabled
 */
package cs1083_assign6;

public class Trap extends Entity{
    
    Trap(int curfloor)
    {
        super(5+curfloor*0.3);
    }
    
}
