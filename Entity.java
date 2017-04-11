/*
 * Mitchell Golding 3552573
 * CS1083 Assignment 6
 * 5 April 2017
 *
 * Purpose: To allow easy comparisons of damage
 * Input: damage values
 * Output: Damage values
 */
package cs1083_assign6;

public class Entity implements Comparable<Entity>{
    private double damage;
    
    Entity(double damage)
    {
        this.damage = damage;
    }
    
    @Override
    public int compareTo(Entity other)
    {
        return Double.compare(this.damage, other.getDamage());
    }
    
    public double getDamage()
    {
        return damage;
    }

    public void setDamage(double damage) 
    {
        this.damage = damage;
    }
}
