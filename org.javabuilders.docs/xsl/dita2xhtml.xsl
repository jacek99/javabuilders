<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:saxon="http://icl.com/saxon" extension-element-prefixes="saxon">





<xsl:import href="xslhtml/dita2htmlImpl.xsl"></xsl:import>


<xsl:import href="xslhtml/taskdisplay.xsl"></xsl:import>


<xsl:import href="xslhtml/refdisplay.xsl"></xsl:import>


<xsl:import href="xslhtml/ut-d.xsl"></xsl:import>

<xsl:import href="xslhtml/sw-d.xsl"></xsl:import>

<xsl:import href="xslhtml/pr-d.xsl"></xsl:import>

<xsl:import href="xslhtml/ui-d.xsl"></xsl:import>

<xsl:import href="xslhtml/hi-d.xsl"></xsl:import>








<xsl:output method="xml" encoding="UTF-8" indent="no" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"></xsl:output>


<xsl:param name="DITAEXT" select="'.xml'"></xsl:param>


<xsl:template match="/">
  <xsl:apply-templates></xsl:apply-templates>
</xsl:template>

</xsl:stylesheet>