package com.nghiemtuananh.readrsskpt

import android.util.Log
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.StringReader
import java.lang.Exception
import javax.xml.XMLConstants
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

class XMLDOMParser {
    fun getDocument(xml: String): Document? {
        var document: Document
        var factory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
        try {
            var db = factory.newDocumentBuilder()
            var iss: InputSource = InputSource()
            iss.characterStream = StringReader(xml)
            iss.encoding = "UTF-8"
            document = db.parse(iss)
        } catch (e: ParserConfigurationException) {
            Log.e("Error: ", e.message, e)
            return null
        } catch (e: SAXException) {
            Log.e("Error: ", e.message, e)
            return null
        } catch (e: IOException) {
            Log.e("Error: ", e.message, e)
            return null
        }
        return document
    }

    fun getValue(item: Element, name: String): String {
        var nodes: NodeList = item.getElementsByTagName(name)
        return this.getTextNodeValue(nodes.item(0))
    }

    private fun getTextNodeValue(elem: Node?): String {
        var child: Node?
        if (elem != null) {
            if (elem.hasChildNodes()) {
                child = elem.firstChild
                while (child != null) {
                    if (child.nodeType == Node.TEXT_NODE) {
                        return child.nodeValue
                    }
                    child = elem.nextSibling
                }
            }
        }
        return ""
    }
}