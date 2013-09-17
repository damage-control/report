package com.github.damagecontrol.report.htmlgenerator

import static org.apache.commons.io.FileUtils.iterateFiles
import static org.apache.commons.io.IOUtils.closeQuietly

class XmlFileReader {

    private static final boolean INCLUDE_SUB_FOLDERS = true
    private static final String[] XML = ['xml']

    def inputFolders

    @SuppressWarnings('CatchException')
    @SuppressWarnings('Println')
    def forEach(closure) {
        inputFolders.each { inputFolder ->
            iterateFiles(inputFolder, XML, INCLUDE_SUB_FOLDERS).each { file ->
                def reader

                if (!file.name.startsWith('TEST-')) {
                    return
                }

                try {
                    reader = new FileReader(file)
                    closure(reader)
                } catch (Exception e) {
                    println "Error reading file '${file}': ${e.message}"
                } finally {
                    closeQuietly(reader)
                }
            }
        }
    }
}
