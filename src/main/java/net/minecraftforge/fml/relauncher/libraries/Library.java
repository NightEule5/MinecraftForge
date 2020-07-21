package net.minecraftforge.fml.relauncher.libraries;

public class Library
{
    public String fileName;
    public String artifact;
    public String timestamp;
    public boolean isEmbedded = false;

    public Artifact toArtifact(Repository repo)
    {
        return new Artifact(repo, artifact, timestamp);
    }
}
