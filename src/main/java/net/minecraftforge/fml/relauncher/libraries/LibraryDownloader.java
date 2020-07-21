package net.minecraftforge.fml.relauncher.libraries;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class LibraryDownloader
{
    private static final Logger LOGGER = LogManager.getLogger();

    private final int timeout;

    public LibraryDownloader()
    {
        this(7000);
    }

    public LibraryDownloader(int timeout)
    {
        this.timeout = timeout;
    }

    public boolean download(RemoteRepository repository, Library library)
    {
        return download(repository, library, LibraryManager.getDefaultRepo());
    }

    public boolean download(RemoteRepository repository, Library library, Repository destination)
    {
        try
        {
            return download(new URL(repository.url), library.toArtifact(destination));
        }
        catch (MalformedURLException e)
        {
            String message = "The repository URL could not be parsed: %s.";
            LOGGER.error(String.format(message, repository.url), e);

            return false;
        }
    }

    public boolean download(URL repository, Artifact artifact)
    {
        return download(repository, artifact, artifact.getRepository());
    }

    public boolean download(URL repository, Artifact artifact, Repository destination)
    {
        try
        {
            return download(new URL(repository, artifact.getPath()), destination.getFile(artifact.getPath()));
        }
        catch (MalformedURLException e)
        {
            String message = "The repository URL, %s, could not be parsed with the provided path: %s.";
            LOGGER.error(String.format(message, repository, artifact.getPath()));

            return false;
        }
    }

    public boolean download(URL url, File destination)
    {
        try
        {
            FileUtils.copyURLToFile(url, destination, timeout, timeout);
            return true;
        }
        catch (IOException e)
        {
            return false;
        }
    }
}
