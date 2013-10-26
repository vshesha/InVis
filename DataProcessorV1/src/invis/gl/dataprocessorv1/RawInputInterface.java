package invis.gl.dataprocessorv1;

import invis.gl.rawinput.RawInputData;


public interface RawInputInterface
{
    public RawInputData getRawInputData();
    public boolean ReadInFile(RawInputData rid) throws Exception;
}
