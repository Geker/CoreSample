package com.zk.back;

import java.io.File;

/**
 * FSVisitor
 * @author Dietrich Featherston
 */
public interface FSVisitor {
    public void visit(File f, byte[] data, String znode);
}
