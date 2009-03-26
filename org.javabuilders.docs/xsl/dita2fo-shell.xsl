<xsl:stylesheet version="1.0" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fox="http://xml.apache.org/fop/extensions">
  
  <xsl:import href="xslfo/topic2foImpl.xsl"></xsl:import>
  <xsl:import href="xslfo/domains2fo.xsl"></xsl:import>
  
  
  <xsl:output method="xml" version="1.0" indent="yes"></xsl:output>
  
  
  <xsl:param name="basic-start-indent">4pt</xsl:param>
  <xsl:param name="basic-end-indent">4pt</xsl:param>
  <xsl:param name="output-related-links" select="'no'"></xsl:param>
  
  <xsl:param name="dflt-ext">.jpg</xsl:param>
  
  
  <xsl:variable name="msgprefix">DOTX</xsl:variable>
  
  
  <xsl:param name="DRAFT" select="'no'"></xsl:param>
  
  <xsl:template match="dita" mode="toplevel">
    <xsl:call-template name="dita-setup"></xsl:call-template>
  </xsl:template>
  <xsl:template match="*[contains(@class,' map/map ')]" mode="toplevel">
    <xsl:call-template name="dita-setup"></xsl:call-template>
  </xsl:template>
  
  <xsl:template match="*[contains(@class,' bkinfo/bkinfo ')]" priority="2">
    
  </xsl:template>
  
  
  <xsl:template name="dita-setup">
    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
      
      <xsl:apply-templates mode="outline"></xsl:apply-templates>
      
      <xsl:call-template name="define-page-masters-dita"></xsl:call-template>
      
      <xsl:call-template name="front-covers"></xsl:call-template>
      
      <xsl:call-template name="generated-frontmatter"></xsl:call-template>
      
      <xsl:call-template name="main-doc3"></xsl:call-template>
      
      
      
    </fo:root>
  </xsl:template>
  
  <xsl:template match="*[contains(@class,' topic/topic ')]" mode="outline">
      <xsl:variable name="id-value">
        <xsl:choose>
          <xsl:when test="@id">
            <xsl:value-of select="@id"></xsl:value-of>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="generate-id()"></xsl:value-of>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:variable>
      
      <fox:outline>
        <xsl:attribute name="internal-destination">
          
          <xsl:value-of select="$id-value"></xsl:value-of>
        </xsl:attribute>
        <fox:label>
          
          <xsl:choose>
            <xsl:when test="*[contains(@class,' topic/titlealts ')]/*[contains(@class, ' topic/navtitle ')]">
              <xsl:apply-templates select="*[contains(@class,' topic/titlealts ')]/*[contains(@class, ' topic/navtitle ')]" mode="text-only"></xsl:apply-templates>
            </xsl:when>
            <xsl:otherwise>
              <xsl:apply-templates select="title" mode="text-only"></xsl:apply-templates>
            </xsl:otherwise>
          </xsl:choose>
        </fox:label>
        <xsl:apply-templates select="child::*[contains(@class,' topic/topic ')]" mode="outline"></xsl:apply-templates>
      </fox:outline>
  </xsl:template>
  
  <xsl:template match="*" mode="text-only">
    <xsl:apply-templates select="text()|*" mode="text-only"></xsl:apply-templates>
  </xsl:template>
  <xsl:template name="define-page-masters-dita">
    <fo:layout-master-set>
      
      <fo:page-sequence-master master-name="chapter-master">
        <fo:repeatable-page-master-alternatives>
          <fo:conditional-page-master-reference page-position="first" odd-or-even="odd" master-reference="common-page"></fo:conditional-page-master-reference>
          
          <fo:conditional-page-master-reference page-position="first" odd-or-even="even" master-reference="common-page"></fo:conditional-page-master-reference>
          
          <fo:conditional-page-master-reference page-position="rest" odd-or-even="odd" master-reference="common-page"></fo:conditional-page-master-reference>
          
          <fo:conditional-page-master-reference page-position="rest" odd-or-even="even" master-reference="common-page"></fo:conditional-page-master-reference>
          
        </fo:repeatable-page-master-alternatives>
      </fo:page-sequence-master>
      <fo:simple-page-master master-name="cover" xsl:use-attribute-sets="common-grid">
        <fo:region-body margin-top="72pt"></fo:region-body>
      </fo:simple-page-master>
      <fo:simple-page-master master-name="common-page" xsl:use-attribute-sets="common-grid">
        <fo:region-body margin-bottom="36pt" margin-top="12pt"></fo:region-body>
        <fo:region-before extent="12pt"></fo:region-before>
        <fo:region-after extent="24pt"></fo:region-after>
      </fo:simple-page-master>
    </fo:layout-master-set>
  </xsl:template>
  <xsl:template name="front-covers">
    
    <fo:page-sequence master-reference="cover">
      <fo:flow flow-name="xsl-region-body">
        <fo:block text-align="right" font-family="Helvetica">
          
          <fo:block font-size="30pt" font-weight="bold" line-height="140%">
          	<xsl:value-of select="//booktitle/mainbooktitle"></xsl:value-of>
          </fo:block>
          
          <fo:block font-size="24pt" font-weight="bold" line-height="140%" margin-bottom="1in">
            <xsl:value-of select="//booktitle/booktitlealt"></xsl:value-of>
          </fo:block>
          
          <xsl:for-each select="//author">
            <xsl:variable name="authorid1" select="generate-id(.)"></xsl:variable>   
			<xsl:variable name="authorid2" select="generate-id(//author[.=current()])"></xsl:variable>
			<xsl:if test="$authorid1=$authorid2">
			  <fo:block font-size="11pt" font-weight="bold" line-height="1.5">
				<xsl:value-of select="."></xsl:value-of>
			  </fo:block>
			</xsl:if>            
		  </xsl:for-each>
		  
          <fo:block font-size="8pt" font-weight="bold" line-height="140%" margin-bottom="1in">
            Version
<xsl:value-of select="//@rev"></xsl:value-of>
          </fo:block>
          
			<fo:block margin-top="3pc" text-align="right" font-size="8pt" line-height="normal">
			© Copyright  2008-2009 Jacek Furmankiewicz
			</fo:block>
        
        
        
        </fo:block>
      </fo:flow>
    </fo:page-sequence>
    
    
  </xsl:template>
  <xsl:template name="place-cover-art">
    
    <fo:block margin-top="2pc" font-family="Helvetica" border-style="dashed" border-color="black" border-width="thin" padding="6pt">
      <fo:block font-size="12pt" line-height="100%" margin-top="12pc" margin-bottom="12pc" text-align="center">
        <fo:inline color="purple" font-weight="bold">[cover art/text goes here]</fo:inline>
        
      </fo:block>
    </fo:block>
  </xsl:template>
  
  
  
  
  
  
  
  
  <xsl:template name="generated-frontmatter">
    <fo:page-sequence master-reference="common-page" format="i" initial-page-number="1">
      
      
      <fo:static-content flow-name="xsl-region-before">
        <fo:block font-size="8pt" line-height="8pt">
          <xsl:value-of select="//booktitle/mainbooktitle"></xsl:value-of> - <xsl:value-of select="//booktitle/booktitlealt"></xsl:value-of> 
        </fo:block>
      </fo:static-content>
      
      <fo:static-content flow-name="xsl-region-after">
        <fo:block text-align="center" font-size="10pt" font-weight="bold" font-family="Helvetica">
          <fo:page-number></fo:page-number>
        </fo:block>
      </fo:static-content>
      
      <fo:flow flow-name="xsl-region-body">
        
        <fo:block line-height="12pt" font-size="10pt" font-family="Helvetica" id="page1-1">
          <fo:block text-align="left" font-family="Helvetica">
            <fo:block>
              <fo:leader color="black" leader-pattern="rule" rule-thickness="3pt" leader-length="2in"></fo:leader>
            </fo:block>
            <fo:block font-size="20pt" font-weight="bold" line-height="140%">
              Contents </fo:block>
            <xsl:call-template name="gen-toc"></xsl:call-template>
          </fo:block>
        </fo:block>
      </fo:flow>
    </fo:page-sequence>
  </xsl:template>
  <xsl:template name="unused-toc">
    
    
    
    
    
  </xsl:template>
  
  <xsl:template name="main-doc3">
    <fo:page-sequence master-reference="chapter-master">
      
      <fo:static-content flow-name="xsl-region-before">
        <fo:block font-size="8pt" line-height="8pt">
          <xsl:value-of select="//booktitle/mainbooktitle"></xsl:value-of> - <xsl:value-of select="//booktitle/booktitlealt"></xsl:value-of>  
        </fo:block>
      </fo:static-content>
      
      <fo:static-content flow-name="xsl-region-after">
        <fo:block text-align="center" font-size="10pt" font-weight="bold" font-family="Helvetica">
          <fo:page-number></fo:page-number>
        </fo:block>
      </fo:static-content>
      
      
      <fo:flow flow-name="xsl-region-body">
        
        <fo:block text-align="left" font-size="10pt" font-family="Helvetica" break-before="page">
          <xsl:apply-templates></xsl:apply-templates>
        </fo:block>
      </fo:flow>
    </fo:page-sequence>
  </xsl:template>
  
  <xsl:attribute-set name="common-grid">
    <xsl:attribute name="page-width">51pc</xsl:attribute>
    
    <xsl:attribute name="page-height">66pc</xsl:attribute>
    
    <xsl:attribute name="margin-top">3pc</xsl:attribute>
    <xsl:attribute name="margin-bottom">3pc</xsl:attribute>
    <xsl:attribute name="margin-left">6pc</xsl:attribute>
    <xsl:attribute name="margin-right">6pc</xsl:attribute>
  </xsl:attribute-set>
  
  <xsl:attribute-set name="maptitle">
    <xsl:attribute name="font-size">16pt</xsl:attribute>
    <xsl:attribute name="font-weight">bold</xsl:attribute>
  </xsl:attribute-set>
  
  <xsl:attribute-set name="mapabstract">
    <xsl:attribute name="margin-top">3pc</xsl:attribute>
    <xsl:attribute name="margin-bottom">3pc</xsl:attribute>
    <xsl:attribute name="margin-left">6pc</xsl:attribute>
    <xsl:attribute name="margin-right">6pc</xsl:attribute>
  </xsl:attribute-set>
  
  <xsl:template name="gen-toc">
    
    <xsl:for-each select="//bookmap/*[contains(@class,' topic/topic ')]|//map/*[contains(@class,' topic/topic ')]">
      <fo:block text-align-last="justify" margin-top="6pt" margin-left="4.9pc">
        <fo:inline font-weight="bold">
          
          <xsl:value-of select="*[contains(@class,' topic/title ')]"></xsl:value-of>
        </fo:inline>
        <fo:leader leader-pattern="dots"></fo:leader>
        <xsl:variable name="id-value">
          <xsl:choose>
            <xsl:when test="@id">
              <xsl:value-of select="@id"></xsl:value-of>
            </xsl:when>
            <xsl:otherwise>
              <xsl:value-of select="generate-id()"></xsl:value-of>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:variable>
        <fo:page-number-citation ref-id="{$id-value}"></fo:page-number-citation>
      </fo:block>
      <xsl:call-template name="get-tce2-section"></xsl:call-template>
    </xsl:for-each>
  </xsl:template>
  
  <xsl:template name="get-tce2-section">
    <xsl:for-each select="*[contains(@class,' topic/topic ')]">
      <fo:block text-align-last="justify" margin-left="7.5pc">
        <fo:inline font-weight="bold">
          <xsl:value-of select="*[contains(@class,' topic/title ')]"></xsl:value-of>
        </fo:inline>
        <fo:leader leader-pattern="dots"></fo:leader>
        <xsl:variable name="id-value">
          <xsl:choose>
            <xsl:when test="@id">
              <xsl:value-of select="@id"></xsl:value-of>
            </xsl:when>
            <xsl:otherwise>
              <xsl:value-of select="generate-id()"></xsl:value-of>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:variable>
        <fo:page-number-citation ref-id="{$id-value}"></fo:page-number-citation>
      </fo:block>
      <xsl:call-template name="get-tce3-section"></xsl:call-template>
    </xsl:for-each>
  </xsl:template>
  
  <xsl:template name="get-tce3-section">
    <xsl:for-each select="*[contains(@class,' topic/topic ')]">
      <fo:block text-align-last="justify" margin-left="9pc">
        <xsl:value-of select="*[contains(@class,' topic/title ')]"></xsl:value-of>
        <fo:leader leader-pattern="dots"></fo:leader>
        <xsl:variable name="id-value">
          <xsl:choose>
            <xsl:when test="@id">
              <xsl:value-of select="@id"></xsl:value-of>
            </xsl:when>
            <xsl:otherwise>
              <xsl:value-of select="generate-id()"></xsl:value-of>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:variable>
        <fo:page-number-citation ref-id="{$id-value}"></fo:page-number-citation>
      </fo:block>
      <xsl:call-template name="get-tce4-section"></xsl:call-template>
    </xsl:for-each>
  </xsl:template>
  
  <xsl:template name="get-tce4-section">
    <xsl:for-each select="bksubsect1">
      <fo:block text-align-last="justify" margin-left="+5.9pc">
        <xsl:value-of select="*/title"></xsl:value-of>
        <fo:leader leader-pattern="dots"></fo:leader>
        <xsl:variable name="id-value">
          <xsl:choose>
            <xsl:when test="@id">
              <xsl:value-of select="@id"></xsl:value-of>
            </xsl:when>
            <xsl:otherwise>
              <xsl:value-of select="generate-id()"></xsl:value-of>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:variable>
        <fo:page-number-citation ref-id="{$id-value}"></fo:page-number-citation>
      </fo:block>
      <xsl:call-template name="get-tce5-section"></xsl:call-template>
    </xsl:for-each>
  </xsl:template>
  
  <xsl:template name="get-tce5-section">
    <xsl:for-each select="bksubsect2">
      <fo:block text-align-last="justify" margin-left="+5.9pc">
        <xsl:value-of select="*/title"></xsl:value-of>
        <fo:leader leader-pattern="dots"></fo:leader>
        <xsl:variable name="id-value">
          <xsl:choose>
            <xsl:when test="@id">
              <xsl:value-of select="@id"></xsl:value-of>
            </xsl:when>
            <xsl:otherwise>
              <xsl:value-of select="generate-id()"></xsl:value-of>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:variable>
        <fo:page-number-citation ref-id="{$id-value}"></fo:page-number-citation>
      </fo:block>
      
    </xsl:for-each>
  </xsl:template>
</xsl:stylesheet>