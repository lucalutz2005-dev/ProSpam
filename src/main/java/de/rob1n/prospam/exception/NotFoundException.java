package de.rob1n.prospam.exception;

/**
 * Created by: robin
 * Date: 05.04.2014
 */
@SuppressWarnings("serial")
public class NotFoundException extends Exception
{
    public NotFoundException(String txt)
    {
        super(txt);
    }
}
