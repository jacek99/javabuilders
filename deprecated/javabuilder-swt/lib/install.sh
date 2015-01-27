echo "Installing required Eclipse JARs into local Maven repository"
mvn install:install-file -Dfile=org.eclipse.core.commands_3.5.0.I20090525-2000.jar -DgroupId=org.eclipse -DartifactId=core-commands -Dversion=3.5.1.SR1 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=org.eclipse.core.databinding_1.2.0.M20090819-0800.jar -DgroupId=org.eclipse -DartifactId=core-databinding -Dversion=3.5.1.SR1 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=org.eclipse.core.databinding.beans_1.2.0.I20090525-2000.jar -DgroupId=org.eclipse -DartifactId=core-databinding-beans -Dversion=3.5.1.SR1 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=org.eclipse.core.databinding.observable_1.2.0.M20090902-0800.jar -DgroupId=org.eclipse -DartifactId=core-databinding-observable -Dversion=3.5.1.SR1 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=org.eclipse.core.databinding.property_1.2.0.M20090819-0800.jar -DgroupId=org.eclipse -DartifactId=core-databinding-property -Dversion=3.5.1.SR1 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=org.eclipse.equinox.common_3.5.1.R35x_v20090807-1100.jar -DgroupId=org.eclipse -DartifactId=equinox-common -Dversion=3.5.1.SR1 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=org.eclipse.jface_3.5.1.M20090826-0800.jar -DgroupId=org.eclipse -DartifactId=jface -Dversion=3.5.1.SR1 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=org.eclipse.jface.databinding_1.3.1.M20090826-0800.jar -DgroupId=org.eclipse -DartifactId=jface-databinding -Dversion=3.5.1.SR1 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=org.eclipse.jface.text_3.5.1.r351_v20090708-0800.jar -DgroupId=org.eclipse -DartifactId=jface-text -Dversion=3.5.1.SR1 -Dpackaging=jar -DgeneratePom=true

echo "Installing SWT"
cd linux
mvn install:install-file -Dfile=swt.jar -DgroupId=org.eclipse -DartifactId=swt-linux-gtk -Dversion=3.5.1.SR1 -Dpackaging=jar -DgeneratePom=true