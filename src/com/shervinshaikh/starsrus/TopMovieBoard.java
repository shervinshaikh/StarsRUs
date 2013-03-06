/**
 * Created with IntelliJ IDEA.
 * User: Sprite
 * Date: 3/5/13
 * Time: 10:33 PM
 * To change this template use File | Settings | File Templates.
 */
 
 package com.shervinshaikh.starsrus;
import javax.swing.*;
public class TopMovieBoard extends JFrame
{

    public TopMovieBoard()
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