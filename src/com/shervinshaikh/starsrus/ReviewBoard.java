/**
 * Created with IntelliJ IDEA.
 * User: Sprite
 * Date: 3/5/13
 * Time: 10:08 PM
 * To change this template use File | Settings | File Templates.
 */
 
 package com.shervinshaikh.starsrus;
import javax.swing.*;
public class ReviewBoard extends JFrame
{

    public ReviewBoard()
    {
        setTitle( "Creating a new Panel" );
        setSize( 350,300 );
        setResizable( false );

        add( new JPanel() );

        setVisible( true );
        setLocationRelativeTo( null );
        setDefaultCloseOperation( EXIT_ON_CLOSE );
    }


}