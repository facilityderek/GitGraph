/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.jgitgraph.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 *
 * @author dfitzsimons
 */
public class LogViewController {

    /**
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger( LogViewController.class.getName() );

    private Git git = null;

    public LogViewController() {
        super();
        git = null;
    }

    public LogViewController( Git git ) {
        this.git = git;
    }

    public Iterable<RevCommit> getGitLog() {
        Iterable<RevCommit> log = null;
        if( git != null ) {
            try {
                log = git.log().call();
            } catch( GitAPIException ex ) {
                LOGGER.log( Level.SEVERE, "Git API Exception", ex );
            }
        }
        return log;
    }

    public String getRepoName() {
        String output = "Unavailable";
        try {
            output = git.getRepository().getFullBranch();
        } catch( IOException ex ) {
            Logger.getLogger( LogViewController.class.getName() ).log( Level.SEVERE, null, ex );
        }
        return output;
    }

}
