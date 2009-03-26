<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:import href="xslrtf/dita2rtfImpl.xsl"></xsl:import>

<xsl:output method="text" encoding="UTF-8"></xsl:output>

<xsl:template match="/">
<xsl:apply-imports></xsl:apply-imports>
</xsl:template>

</xsl:stylesheet>