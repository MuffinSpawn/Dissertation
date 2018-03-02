/*
 * AluminumDiskOnRing.java
 */

import com.comsol.model.*;
import com.comsol.model.util.*;

/** Model exported on Jan 17 2016, 15:26 by COMSOL 4.4.0.195. */
public class AluminumDiskOnRing {

  public static void main(String[] args) {
    run();
  }

  public static Model run() {
    Model model = ModelUtil.create("Model");

    model.modelPath("C:\\Users\\plane\\Desktop\\COMSOL");

    model.name("Aluminum Disk on Ring.mph");

    model
         .comments("Hollow Cylinder\n\nIn this model of acoustic-structure interaction, pressure waves are generated from a point source or a line source inside a water-filled capped metal cylinder. The sound propagates through the cylinder and the surrounding water.");

    model.param()
         .set("diskRadius", "15.24 [cm]", "RF cavity outer diameter (15.24[cm])");
    model.param().set("diskHeight", "5.08 [cm]", "End cap height (5.08[cm])");
    model.param().set("ringHeight", "8.22 [cm]", "Center flange height");
    model.param().set("sourceRadius", "2 [mm]");
    model.param()
         .set("sourceRho", "12 [cm]", "Distance to source from z-axis");
    model.param().set("sourceAngle", "0 [rad]", "Source angle");
    model.param()
         .set("sourceX", "sourceRho * cos(sourceAngle)", "Source x position");
    model.param()
         .set("sourceY", "sourceRho * sin(sourceAngle)", "Source y position");
    model.param()
         .set("sourceZ", "2*diskHeight + ringHeight", "Source z position");
    model.param().set("t0", "0.1 [ms]", "Spark duration");
    model.param().set("sourceH", "1.0 [mm]");
    model.param().set("gamma", "3", "Spark decay rate");
    model.param().set("lambda", "1", "Cycles per t0");
    model.param().set("F0", "1 [N]", "Spark force magnitude");
    model.param()
         .set("f0", "2.17e4 [N/m^2]", "Spark force density magnitude");
    model.param().set("t1", "0 [us]", "Upstream spark delay");
    model.param().set("t2", "t0 + t1", "Upstream spark end time");
    model.param().set("micRadius", "1.27 [cm]");
    model.param().set("micHeight", "1.0 [cm]");
    model.param().set("micRho", "12 [cm]");
    model.param().set("micAngle", "pi [rad]");
    model.param().set("micX", "micRho * cos(micAngle)");
    model.param().set("micY", "micRho * sin(micAngle)");
    model.param().set("micZ", "2*diskHeight + ringHeight");
    model.param().set("alpha_dM", "0");
    model.param().set("beta_dK", "1e-7", "Stiffness damping factor");
    model.param().set("k_tot_r", "1e15 [N/m]");
    model.param().set("k_tot_z", "1e15 [N/m]");
    model.param().set("k_tot_spring", "1e8 [N/m]");

    model.modelNode().create("mod1");
    model.modelNode("mod1").name("Model 1");

    model.func().create("pw3", "Piecewise");
    model.func("pw3").model("mod1");
    model.func("pw3").name("Impact Force Magnitude");
    model.func("pw3")
         .set("pieces", new String[][]{{"0", "t0", "F0*exp(-gamma/t0*t)*sin(2*pi*lambda/t0*t)"}});
    model.func("pw3").set("argunit", "s");
    model.func("pw3").set("fununit", "N");
    model.func("pw3").set("funcname", "Impact");
    model.func("pw3").set("arg", "t");

    model.geom().create("geom1", 3);
    model.geom("geom1").lengthUnit("mm");
    model.geom("geom1").geomRep("comsol");
    model.geom("geom1").feature().create("cyl1", "Cylinder");
    model.geom("geom1").feature().create("cyl4", "Cylinder");
    model.geom("geom1").feature().create("cyl5", "Cylinder");
    model.geom("geom1").feature().create("dif1", "Difference");
    model.geom("geom1").feature().create("cyl7", "Cylinder");
    model.geom("geom1").feature().create("par1", "Partition");
    model.geom("geom1").feature().create("cyl6", "Cylinder");
    model.geom("geom1").feature().create("cyl3", "Cylinder");
    model.geom("geom1").feature().create("pt2", "Point");
    model.geom("geom1").feature().create("pt3", "Point");
    model.geom("geom1").feature().create("blk1", "Block");
    model.geom("geom1").feature().create("blk2", "Block");
    model.geom("geom1").feature().create("blk3", "Block");
    model.geom("geom1").feature("cyl1").active(false);
    model.geom("geom1").feature("cyl1").name("Upstream Disk");
    model.geom("geom1").feature("cyl1").set("r", "diskRadius");
    model.geom("geom1").feature("cyl1")
         .set("pos", new String[]{"0", "0", "0"});
    model.geom("geom1").feature("cyl1").set("h", "diskHeight");
    model.geom("geom1").feature("cyl4").set("r", "6 [inch]");
    model.geom("geom1").feature("cyl4")
         .set("pos", new String[]{"0", "0", "diskHeight"});
    model.geom("geom1").feature("cyl4").set("h", "ringHeight");
    model.geom("geom1").feature("cyl5").set("r", "4.5 [inch]");
    model.geom("geom1").feature("cyl5")
         .set("pos", new String[]{"0", "0", "diskHeight"});
    model.geom("geom1").feature("cyl5").set("h", "ringHeight");
    model.geom("geom1").feature("dif1").name("Ring");
    model.geom("geom1").feature("dif1").selection("input")
         .set(new String[]{"cyl4"});
    model.geom("geom1").feature("dif1").selection("input2")
         .set(new String[]{"cyl5"});
    model.geom("geom1").feature("cyl7").set("r", "diskRadius");
    model.geom("geom1").feature("cyl7")
         .set("pos", new String[]{"0", "0", "diskHeight"});
    model.geom("geom1").feature("cyl7").set("h", "ringHeight/6");
    model.geom("geom1").feature("par1").selection("tool")
         .set(new String[]{"cyl7"});
    model.geom("geom1").feature("par1").selection("input")
         .set(new String[]{"dif1"});
    model.geom("geom1").feature("cyl6").name("Downstream Disk");
    model.geom("geom1").feature("cyl6").set("r", "diskRadius");
    model.geom("geom1").feature("cyl6")
         .set("pos", new String[]{"0", "0", "diskHeight+ringHeight"});
    model.geom("geom1").feature("cyl6").set("h", "diskHeight");
    model.geom("geom1").feature("cyl3").name("Source");
    model.geom("geom1").feature("cyl3").set("r", "sourceRadius");
    model.geom("geom1").feature("cyl3")
         .set("axis", new String[]{"0", "0", "1"});
    model.geom("geom1").feature("cyl3")
         .set("pos", new String[]{"sourceX", "sourceY", "sourceZ"});
    model.geom("geom1").feature("cyl3").set("h", "sourceRadius");
    model.geom("geom1").feature("pt2").name("Impact Point");
    model.geom("geom1").feature("pt2").setIndex("p", "sourceX", 0, 0);
    model.geom("geom1").feature("pt2").setIndex("p", "sourceY", 1, 0);
    model.geom("geom1").feature("pt2").setIndex("p", "sourceZ", 2, 0);
    model.geom("geom1").feature("pt3").name("Pick Up Point");
    model.geom("geom1").feature("pt3").setIndex("p", "micX", 0, 0);
    model.geom("geom1").feature("pt3").setIndex("p", "micY", 1, 0);
    model.geom("geom1").feature("pt3").setIndex("p", "micZ", 2, 0);
    model.geom("geom1").feature("blk1").name("Rail 1");
    model.geom("geom1").feature("blk1").set("rot", "-30");
    model.geom("geom1").feature("blk1")
         .set("size", new String[]{"0.25 [inch]", "5.5 [inch]", "0.25 [inch]"});
    model.geom("geom1").feature("blk1")
         .set("pos", new String[]{"57.15", "98.99", "diskHeight-0.125 [inch]"});
    model.geom("geom1").feature("blk1").set("base", "center");
    model.geom("geom1").feature("blk2").name("Rail 2");
    model.geom("geom1").feature("blk2").set("rot", "90");
    model.geom("geom1").feature("blk2")
         .set("size", new String[]{"0.25 [inch]", "5.5 [inch]", "0.25 [inch]"});
    model.geom("geom1").feature("blk2")
         .set("pos", new String[]{"-114.3", "0", "diskHeight-0.125 [inch]"});
    model.geom("geom1").feature("blk2").set("base", "center");
    model.geom("geom1").feature("blk3").name("Rail 3");
    model.geom("geom1").feature("blk3").set("rot", "30");
    model.geom("geom1").feature("blk3")
         .set("size", new String[]{"0.25 [inch]", "5.5 [inch]", "0.25 [inch]"});
    model.geom("geom1").feature("blk3")
         .set("pos", new String[]{"57.15", "-98.99", "diskHeight-0.125 [inch]"});
    model.geom("geom1").feature("blk3").set("base", "center");
    model.geom("geom1").run();

    model.view().create("view2", 2);

    model.material().create("mat1");
    model.material("mat1").propertyGroup()
         .create("Enu", "Young's modulus and Poisson's ratio");
    model.material("mat1").propertyGroup().create("Murnaghan", "Murnaghan");
    model.material("mat1").propertyGroup()
         .create("Lame", "Lam\u00e9 parameters");
    model.material().create("mat2");
    model.material("mat2").selection().set(new int[]{7});

    model.physics().create("solid", "SolidMechanics", "geom1");
    model.physics("solid").feature("lemm1").feature()
         .create("dmp1", "Damping", 3);
    model.physics("solid").feature("lemm1").feature("dmp1").selection()
         .set(new int[]{2, 3, 4, 7});
    model.physics("solid").feature().create("bl1", "BodyLoad", 3);
    model.physics("solid").feature("bl1").selection().set(new int[]{7});
    model.physics("solid").feature().create("bl2", "BodyLoad", 3);
    model.physics("solid").feature("bl2").selection().set(new int[]{7});
    model.physics("solid").feature().create("fix1", "Fixed", 2);
    model.physics("solid").feature("fix1").selection().set(new int[]{14, 23});
    model.physics("solid").feature().create("tel1", "ThinElasticLayer", 2);
    model.physics("solid").feature("tel1").selection().set(new int[]{14});
    model.physics("solid").feature().create("spf1", "SpringFoundation2", 2);
    model.physics("solid").feature("spf1").selection()
         .set(new int[]{8, 16, 17, 45, 46, 47});
    model.physics("solid").feature()
         .create("lrb1", "LowReflectingBoundary", 2);
    model.physics("solid").feature("lrb1").selection()
         .set(new int[]{3, 8, 37, 41, 45, 46});

    model.mesh().create("mesh1", "geom1");
    model.mesh("mesh1").feature().create("ftet1", "FreeTet");
    model.mesh("mesh1").feature("ftet1").feature().create("size4", "Size");
    model.mesh("mesh1").feature("ftet1").feature("size4").selection()
         .geom("geom1", 3);
    model.mesh("mesh1").feature("ftet1").feature("size4").selection()
         .set(new int[]{7});

    model.result().table().create("evl3", "Table");

    model.view("view1").set("transparency", "on");
    model.view("view2").axis().set("xmin", "-2.730570077896118");
    model.view("view2").axis().set("xmax", "2.730570077896118");

    model.material("mat1").name("Aluminum");
    model.material("mat1").propertyGroup("def")
         .set("relpermeability", new String[]{"1", "0", "0", "0", "1", "0", "0", "0", "1"});
    model.material("mat1").propertyGroup("def")
         .set("heatcapacity", "900[J/(kg*K)]");
    model.material("mat1").propertyGroup("def")
         .set("thermalconductivity", new String[]{"238[W/(m*K)]", "0", "0", "0", "238[W/(m*K)]", "0", "0", "0", "238[W/(m*K)]"});
    model.material("mat1").propertyGroup("def")
         .set("electricconductivity", new String[]{"3.774e7[S/m]", "0", "0", "0", "3.774e7[S/m]", "0", "0", "0", "3.774e7[S/m]"});
    model.material("mat1").propertyGroup("def")
         .set("relpermittivity", new String[]{"1", "0", "0", "0", "1", "0", "0", "0", "1"});
    model.material("mat1").propertyGroup("def")
         .set("thermalexpansioncoefficient", new String[]{"23e-6[1/K]", "0", "0", "0", "23e-6[1/K]", "0", "0", "0", "23e-6[1/K]"});
    model.material("mat1").propertyGroup("def")
         .set("density", "2700[kg/m^3]");
    model.material("mat1").propertyGroup("Enu")
         .set("youngsmodulus", "70e9[Pa]");
    model.material("mat1").propertyGroup("Enu").set("poissonsratio", "0.33");
    model.material("mat1").propertyGroup("Murnaghan").set("l", "");
    model.material("mat1").propertyGroup("Murnaghan").set("m", "");
    model.material("mat1").propertyGroup("Murnaghan").set("n", "");
    model.material("mat1").propertyGroup("Murnaghan").set("l", "-2.5e11[Pa]");
    model.material("mat1").propertyGroup("Murnaghan").set("m", "-3.3e11[Pa]");
    model.material("mat1").propertyGroup("Murnaghan").set("n", "-3.5e11[Pa]");
    model.material("mat1").propertyGroup("Lame").set("lambLame", "");
    model.material("mat1").propertyGroup("Lame").set("muLame", "");
    model.material("mat1").propertyGroup("Lame")
         .set("lambLame", "5.1e10[Pa]");
    model.material("mat1").propertyGroup("Lame").set("muLame", "2.6e10[Pa]");
    model.material("mat2").active(false);
    model.material("mat2").name("316 Stainless Steel");
    model.material("mat2").propertyGroup("def").set("density", "7.99e3");
    model.material("mat2").propertyGroup("def").set("soundspeed", "5700");
    model.material("mat2").propertyGroup("def")
         .set("relpermeability", new String[]{"1", "0", "0", "0", "1", "0", "0", "0", "1"});
    model.material("mat2").propertyGroup("def")
         .set("electricconductivity", new String[]{"1.42e6", "0", "0", "0", "1.42e6", "0", "0", "0", "1.42e6"});
    model.material("mat2").propertyGroup("def")
         .set("thermalexpansioncoefficient", new String[]{"1.6e-5", "0", "0", "0", "1.6e-5", "0", "0", "0", "1.6e-5"});
    model.material("mat2").propertyGroup("def").set("heatcapacity", "500");
    model.material("mat2").propertyGroup("def")
         .set("relpermittivity", new String[]{"1", "0", "0", "0", "1", "0", "0", "0", "1"});
    model.material("mat2").propertyGroup("def")
         .set("thermalconductivity", new String[]{"16.2", "0", "0", "0", "16.2", "0", "0", "0", "16.2"});
    model.material("mat2").propertyGroup("def").set("youngsmodulus", "194e9");
    model.material("mat2").propertyGroup("def").set("poissonsratio", "0.30");
    model.material("mat2").propertyGroup("def").set("lossfactor", ".04");

    model.physics("solid").feature("lemm1").feature("dmp1")
         .set("alpha_dM", "alpha_dM");
    model.physics("solid").feature("lemm1").feature("dmp1")
         .set("beta_dK", "beta_dK");
    model.physics("solid").feature("bl1")
         .set("Ftot", new String[][]{{"0"}, {"0"}, {"-Impact(t)"}});
    model.physics("solid").feature("bl1").set("LoadType", "TotalForce");
    model.physics("solid").feature("bl1").name("Impact Source Impulse");
    model.physics("solid").feature("bl2")
         .set("Ftot", new String[][]{{"0"}, {"0"}, {"-1"}});
    model.physics("solid").feature("bl2").set("LoadType", "TotalForce");
    model.physics("solid").feature("bl2")
         .name("Impact Source Impulse (Eigenfrequencies)");
    model.physics("solid").feature("fix1").active(false);
    model.physics("solid").feature("tel1").set("SpringType", "kTot");
    model.physics("solid").feature("tel1")
         .set("kTot", new String[][]{{"k_tot_r"}, {"k_tot_r"}, {"k_tot_z"}});
    model.physics("solid").feature("spf1").set("SpringType", "kTot");
    model.physics("solid").feature("spf1")
         .set("kTot", new String[][]{{"k_tot_spring"}, {"k_tot_spring"}, {"k_tot_spring"}});
    model.physics("solid").feature("spf1").active(false);
    model.physics("solid").feature("lrb1").active(false);

    model.mesh("mesh1").feature("ftet1").feature("size4").name("Source");
    model.mesh("mesh1").feature("ftet1").feature("size4").set("hauto", 3);
    model.mesh("mesh1").run();

    model.result().table("evl3").name("Evaluation 3D");
    model.result().table("evl3").comments("Interactive 3D values");

    model.study().create("std2");
    model.study("std2").feature().create("time", "Transient");
    model.study().create("std3");
    model.study("std3").feature().create("eig", "Eigenfrequency");
    model.study().create("std4");
    model.study("std4").feature().create("param", "Parametric");
    model.study("std4").feature().create("time", "Transient");

    model.sol().create("sol6");
    model.sol("sol6").study("std2");
    model.sol("sol6").attach("std2");
    model.sol("sol6").feature().create("st1", "StudyStep");
    model.sol("sol6").feature().create("v1", "Variables");
    model.sol("sol6").feature().create("t1", "Time");
    model.sol("sol6").feature("t1").feature().create("fc1", "FullyCoupled");
    model.sol("sol6").feature("t1").feature().remove("fcDef");

    model.study("std2").feature("time").set("initstudyhide", "on");
    model.study("std2").feature("time").set("initsolhide", "on");
    model.study("std2").feature("time").set("notstudyhide", "on");
    model.study("std2").feature("time").set("notsolhide", "on");
    model.study("std3").feature("eig").set("initstudyhide", "on");
    model.study("std3").feature("eig").set("initsolhide", "on");
    model.study("std3").feature("eig").set("notstudyhide", "on");
    model.study("std3").feature("eig").set("notsolhide", "on");
    model.study("std4").feature("time").set("initstudyhide", "on");
    model.study("std4").feature("time").set("initsolhide", "on");
    model.study("std4").feature("time").set("notstudyhide", "on");
    model.study("std4").feature("time").set("notsolhide", "on");

    model.sol().create("sol16");
    model.sol("sol16").study("std3");
    model.sol("sol16").attach("std3");
    model.sol("sol16").feature().create("st1", "StudyStep");
    model.sol("sol16").feature().create("v1", "Variables");
    model.sol("sol16").feature().create("e1", "Eigenvalue");

    model.study("std2").feature("time").set("initstudyhide", "on");
    model.study("std2").feature("time").set("initsolhide", "on");
    model.study("std2").feature("time").set("notstudyhide", "on");
    model.study("std2").feature("time").set("notsolhide", "on");
    model.study("std3").feature("eig").set("initstudyhide", "on");
    model.study("std3").feature("eig").set("initsolhide", "on");
    model.study("std3").feature("eig").set("notstudyhide", "on");
    model.study("std3").feature("eig").set("notsolhide", "on");
    model.study("std4").feature("time").set("initstudyhide", "on");
    model.study("std4").feature("time").set("initsolhide", "on");
    model.study("std4").feature("time").set("notstudyhide", "on");
    model.study("std4").feature("time").set("notsolhide", "on");

    model.sol().create("sol17");
    model.sol("sol17").study("std4");
    model.sol("sol17").attach("std4");
    model.sol("sol17").feature().create("st1", "StudyStep");
    model.sol("sol17").feature().create("v1", "Variables");
    model.sol("sol17").feature().create("t1", "Time");
    model.sol("sol17").feature("t1").feature().create("fc1", "FullyCoupled");
    model.sol("sol17").feature("t1").feature().remove("fcDef");

    model.study("std2").feature("time").set("initstudyhide", "on");
    model.study("std2").feature("time").set("initsolhide", "on");
    model.study("std2").feature("time").set("notstudyhide", "on");
    model.study("std2").feature("time").set("notsolhide", "on");
    model.study("std3").feature("eig").set("initstudyhide", "on");
    model.study("std3").feature("eig").set("initsolhide", "on");
    model.study("std3").feature("eig").set("notstudyhide", "on");
    model.study("std3").feature("eig").set("notsolhide", "on");
    model.study("std4").feature("time").set("initstudyhide", "on");
    model.study("std4").feature("time").set("initsolhide", "on");
    model.study("std4").feature("time").set("notstudyhide", "on");
    model.study("std4").feature("time").set("notsolhide", "on");

    model.sol().create("sol18");
    model.sol("sol18").study("std4");
    model.sol("sol18").feature().create("su1", "StoreSolution");
    model.sol("sol18").feature().create("su2", "StoreSolution");
    model.sol("sol18").feature().create("su3", "StoreSolution");
    model.sol("sol18").feature().create("su4", "StoreSolution");
    model.sol("sol18").feature().create("su5", "StoreSolution");
    model.sol("sol18").feature().create("su6", "StoreSolution");
    model.sol("sol18").feature().create("su7", "StoreSolution");
    model.sol("sol18").feature().create("su8", "StoreSolution");

    model.study("std2").feature("time").set("initstudyhide", "on");
    model.study("std2").feature("time").set("initsolhide", "on");
    model.study("std2").feature("time").set("notstudyhide", "on");
    model.study("std2").feature("time").set("notsolhide", "on");
    model.study("std3").feature("eig").set("initstudyhide", "on");
    model.study("std3").feature("eig").set("initsolhide", "on");
    model.study("std3").feature("eig").set("notstudyhide", "on");
    model.study("std3").feature("eig").set("notsolhide", "on");
    model.study("std4").feature("time").set("initstudyhide", "on");
    model.study("std4").feature("time").set("initsolhide", "on");
    model.study("std4").feature("time").set("notstudyhide", "on");
    model.study("std4").feature("time").set("notsolhide", "on");

    model.sol("sol19").study("std4");

    model.study("std2").feature("time").set("initstudyhide", "on");
    model.study("std2").feature("time").set("initsolhide", "on");
    model.study("std2").feature("time").set("notstudyhide", "on");
    model.study("std2").feature("time").set("notsolhide", "on");
    model.study("std3").feature("eig").set("initstudyhide", "on");
    model.study("std3").feature("eig").set("initsolhide", "on");
    model.study("std3").feature("eig").set("notstudyhide", "on");
    model.study("std3").feature("eig").set("notsolhide", "on");
    model.study("std4").feature("time").set("initstudyhide", "on");
    model.study("std4").feature("time").set("initsolhide", "on");
    model.study("std4").feature("time").set("notstudyhide", "on");
    model.study("std4").feature("time").set("notsolhide", "on");

    model.sol("sol20").study("std4");

    model.study("std2").feature("time").set("initstudyhide", "on");
    model.study("std2").feature("time").set("initsolhide", "on");
    model.study("std2").feature("time").set("notstudyhide", "on");
    model.study("std2").feature("time").set("notsolhide", "on");
    model.study("std3").feature("eig").set("initstudyhide", "on");
    model.study("std3").feature("eig").set("initsolhide", "on");
    model.study("std3").feature("eig").set("notstudyhide", "on");
    model.study("std3").feature("eig").set("notsolhide", "on");
    model.study("std4").feature("time").set("initstudyhide", "on");
    model.study("std4").feature("time").set("initsolhide", "on");
    model.study("std4").feature("time").set("notstudyhide", "on");
    model.study("std4").feature("time").set("notsolhide", "on");

    model.sol("sol21").study("std4");

    model.study("std2").feature("time").set("initstudyhide", "on");
    model.study("std2").feature("time").set("initsolhide", "on");
    model.study("std2").feature("time").set("notstudyhide", "on");
    model.study("std2").feature("time").set("notsolhide", "on");
    model.study("std3").feature("eig").set("initstudyhide", "on");
    model.study("std3").feature("eig").set("initsolhide", "on");
    model.study("std3").feature("eig").set("notstudyhide", "on");
    model.study("std3").feature("eig").set("notsolhide", "on");
    model.study("std4").feature("time").set("initstudyhide", "on");
    model.study("std4").feature("time").set("initsolhide", "on");
    model.study("std4").feature("time").set("notstudyhide", "on");
    model.study("std4").feature("time").set("notsolhide", "on");

    model.sol("sol22").study("std4");

    model.study("std2").feature("time").set("initstudyhide", "on");
    model.study("std2").feature("time").set("initsolhide", "on");
    model.study("std2").feature("time").set("notstudyhide", "on");
    model.study("std2").feature("time").set("notsolhide", "on");
    model.study("std3").feature("eig").set("initstudyhide", "on");
    model.study("std3").feature("eig").set("initsolhide", "on");
    model.study("std3").feature("eig").set("notstudyhide", "on");
    model.study("std3").feature("eig").set("notsolhide", "on");
    model.study("std4").feature("time").set("initstudyhide", "on");
    model.study("std4").feature("time").set("initsolhide", "on");
    model.study("std4").feature("time").set("notstudyhide", "on");
    model.study("std4").feature("time").set("notsolhide", "on");

    model.sol("sol23").study("std4");

    model.study("std2").feature("time").set("initstudyhide", "on");
    model.study("std2").feature("time").set("initsolhide", "on");
    model.study("std2").feature("time").set("notstudyhide", "on");
    model.study("std2").feature("time").set("notsolhide", "on");
    model.study("std3").feature("eig").set("initstudyhide", "on");
    model.study("std3").feature("eig").set("initsolhide", "on");
    model.study("std3").feature("eig").set("notstudyhide", "on");
    model.study("std3").feature("eig").set("notsolhide", "on");
    model.study("std4").feature("time").set("initstudyhide", "on");
    model.study("std4").feature("time").set("initsolhide", "on");
    model.study("std4").feature("time").set("notstudyhide", "on");
    model.study("std4").feature("time").set("notsolhide", "on");

    model.sol("sol24").study("std4");

    model.study("std2").feature("time").set("initstudyhide", "on");
    model.study("std2").feature("time").set("initsolhide", "on");
    model.study("std2").feature("time").set("notstudyhide", "on");
    model.study("std2").feature("time").set("notsolhide", "on");
    model.study("std3").feature("eig").set("initstudyhide", "on");
    model.study("std3").feature("eig").set("initsolhide", "on");
    model.study("std3").feature("eig").set("notstudyhide", "on");
    model.study("std3").feature("eig").set("notsolhide", "on");
    model.study("std4").feature("time").set("initstudyhide", "on");
    model.study("std4").feature("time").set("initsolhide", "on");
    model.study("std4").feature("time").set("notstudyhide", "on");
    model.study("std4").feature("time").set("notsolhide", "on");

    model.sol("sol25").study("std4");

    model.study("std2").feature("time").set("initstudyhide", "on");
    model.study("std2").feature("time").set("initsolhide", "on");
    model.study("std2").feature("time").set("notstudyhide", "on");
    model.study("std2").feature("time").set("notsolhide", "on");
    model.study("std3").feature("eig").set("initstudyhide", "on");
    model.study("std3").feature("eig").set("initsolhide", "on");
    model.study("std3").feature("eig").set("notstudyhide", "on");
    model.study("std3").feature("eig").set("notsolhide", "on");
    model.study("std4").feature("time").set("initstudyhide", "on");
    model.study("std4").feature("time").set("initsolhide", "on");
    model.study("std4").feature("time").set("notstudyhide", "on");
    model.study("std4").feature("time").set("notsolhide", "on");

    model.sol("sol26").study("std4");

    model.study("std2").feature("time").set("initstudyhide", "on");
    model.study("std2").feature("time").set("initsolhide", "on");
    model.study("std2").feature("time").set("notstudyhide", "on");
    model.study("std2").feature("time").set("notsolhide", "on");
    model.study("std3").feature("eig").set("initstudyhide", "on");
    model.study("std3").feature("eig").set("initsolhide", "on");
    model.study("std3").feature("eig").set("notstudyhide", "on");
    model.study("std3").feature("eig").set("notsolhide", "on");
    model.study("std4").feature("time").set("initstudyhide", "on");
    model.study("std4").feature("time").set("initsolhide", "on");
    model.study("std4").feature("time").set("notstudyhide", "on");
    model.study("std4").feature("time").set("notsolhide", "on");

    model.batch().create("p1", "Parametric");
    model.batch("p1").feature().create("so1", "Solutionseq");
    model.batch("p1").study("std4");

    model.result().dataset().create("pw5_ds1", "Function1D");
    model.result().dataset().create("pw5_ds2", "Function1D");
    model.result().dataset().create("pw5_ds3", "Function1D");
    model.result().dataset().create("pw3_ds1", "Function1D");
    model.result().dataset().create("pw3_ds2", "Function1D");
    model.result().dataset().create("pw3_ds3", "Function1D");
    model.result().dataset().create("pw5_ds4", "Function1D");
    model.result().dataset().create("pw5_ds5", "Function1D");
    model.result().dataset().create("pw5_ds6", "Function1D");
    model.result().dataset().create("pw3_ds4", "Function1D");
    model.result().dataset().create("pw3_ds5", "Function1D");
    model.result().dataset().create("pw3_ds6", "Function1D");
    model.result().dataset().create("pw3_ds7", "Function1D");
    model.result().dataset().create("pw3_ds8", "Function1D");
    model.result().dataset().create("pw3_ds9", "Function1D");
    model.result().dataset("dset3").set("solution", "sol6");
    model.result().dataset("dset4").set("solution", "sol16");
    model.result().dataset("dset5").set("solution", "sol17");
    model.result().dataset("dset6").set("solution", "sol18");
    model.result().dataset().remove("dset1");
    model.result().dataset().remove("dset2");
    model.result().dataset().remove("dset7");
    model.result().dataset().remove("dset8");
    model.result().dataset().remove("dset9");
    model.result().dataset().remove("dset10");
    model.result().dataset().remove("dset11");
    model.result().dataset().remove("dset12");
    model.result().create("pg10", "PlotGroup1D");
    model.result().create("pg11", "PlotGroup3D");
    model.result().create("pg12", "PlotGroup3D");
    model.result().create("pg13", "PlotGroup1D");
    model.result().create("pg14", "PlotGroup1D");
    model.result().create("pg15", "PlotGroup3D");
    model.result().create("pg16", "PlotGroup3D");
    model.result("pg10").feature().create("ptgr1", "PointGraph");
    model.result("pg10").feature().create("ptgr2", "PointGraph");
    model.result("pg10").feature().create("ptgr3", "PointGraph");
    model.result("pg10").feature("ptgr1").selection().set(new int[]{66});
    model.result("pg10").feature("ptgr2").selection().set(new int[]{11});
    model.result("pg10").feature("ptgr3").selection().set(new int[]{11});
    model.result("pg11").feature().create("surf1", "Surface");
    model.result("pg11").feature("surf1").feature().create("def", "Deform");
    model.result("pg12").feature().create("surf1", "Surface");
    model.result("pg12").feature("surf1").feature().create("def", "Deform");
    model.result("pg13").feature().create("plot1", "LineGraph");
    model.result("pg13").feature().create("plot2", "LineGraph");
    model.result("pg13").feature().create("plot3", "LineGraph");
    model.result("pg14").feature().create("plot1", "LineGraph");
    model.result("pg14").feature().create("plot2", "LineGraph");
    model.result("pg14").feature().create("plot3", "LineGraph");
    model.result("pg15").feature().create("surf1", "Surface");
    model.result("pg15").feature().create("arwl1", "ArrowLine");
    model.result("pg15").feature("surf1").feature().create("def", "Deform");
    model.result("pg16").feature().create("surf1", "Surface");
    model.result("pg16").feature("surf1").feature().create("def", "Deform");
    model.result().export().create("plot1", "Plot");

    model.study("std2").name("Time-Dependent Single Study");
    model.study("std2").feature("time")
         .set("disabledphysics", new String[]{"solid/bl2"});
    model.study("std2").feature("time").set("tunit", "\u00b5s");
    model.study("std2").feature("time").set("useadvanceddisable", true);
    model.study("std2").feature("time").set("tlist", "range(0,25,25000)");
    model.study("std2").feature("time").set("rtolactive", true);
    model.study("std2").feature("time").set("rtol", "1e-6");
    model.study("std3").feature("eig")
         .set("disabledphysics", new String[]{"solid/bl1"});
    model.study("std3").feature("eig").set("useadvanceddisable", true);
    model.study("std3").feature("eig").set("shift", "5000");
    model.study("std4").feature("param").set("pname", new String[]{"t0"});
    model.study("std4").feature("param")
         .set("plistarr", new String[]{"range(0.02 [ms],0.02 [ms],0.08 [ms])"});
    model.study("std4").feature("time")
         .set("disabledphysics", new String[]{"solid/bl2"});
    model.study("std4").feature("time").set("tunit", "\u00b5s");
    model.study("std4").feature("time").set("useadvanceddisable", true);
    model.study("std4").feature("time").set("tlist", "range(0,25,25000)");
    model.study("std4").feature("time").set("rtolactive", true);
    model.study("std4").feature("time").set("rtol", "1e-6");

    model.sol("sol6").attach("std2");
    model.sol("sol6").feature("st1")
         .name("Compile Equations: Time Dependent");
    model.sol("sol6").feature("st1").set("studystep", "time");
    model.sol("sol6").feature("v1").set("control", "time");
    model.sol("sol6").feature("v1").feature("mod1_u")
         .set("scalemethod", "manual");
    model.sol("sol6").feature("v1").feature("mod1_u")
         .set("scaleval", "1e-2*0.4342740148800065");
    model.sol("sol6").feature("t1").set("tlist", "range(0,25,25000)");
    model.sol("sol6").feature("t1").set("timemethod", "genalpha");
    model.sol("sol6").feature("t1").set("timestepgenalpha", "25e-6");
    model.sol("sol6").feature("t1").set("tstepsgenalpha", "manual");
    model.sol("sol6").feature("t1").set("control", "time");
    model.sol("sol6").feature("t1").set("rtol", "1e-6");
    model.sol("sol6").feature("t1").set("rhoinf", "1");
    model.sol("sol6").feature("t1").set("tunit", "\u00b5s");
    model.sol("sol16").attach("std3");
    model.sol("sol16").feature("st1")
         .name("Compile Equations: Eigenfrequency");
    model.sol("sol16").feature("st1").set("study", "std3");
    model.sol("sol16").feature("v1").set("control", "eig");
    model.sol("sol16").feature("e1").set("control", "eig");
    model.sol("sol16").feature("e1").set("transform", "eigenfrequency");
    model.sol("sol16").feature("e1").set("shift", "5000");
    model.sol("sol17").attach("std4");
    model.sol("sol17").feature("st1")
         .name("Compile Equations: Time Dependent");
    model.sol("sol17").feature("st1").set("studystep", "time");
    model.sol("sol17").feature("st1").set("study", "std4");
    model.sol("sol17").feature("v1").set("control", "time");
    model.sol("sol17").feature("v1").feature("mod1_u")
         .set("scalemethod", "manual");
    model.sol("sol17").feature("v1").feature("mod1_u")
         .set("scaleval", "1e-2*0.4868460727462568");
    model.sol("sol17").feature("t1").set("tlist", "range(0,25,25000)");
    model.sol("sol17").feature("t1").set("timemethod", "genalpha");
    model.sol("sol17").feature("t1").set("timestepgenalpha", "25e-6");
    model.sol("sol17").feature("t1").set("tstepsgenalpha", "manual");
    model.sol("sol17").feature("t1").set("control", "time");
    model.sol("sol17").feature("t1").set("rtol", "1e-6");
    model.sol("sol17").feature("t1").set("tunit", "\u00b5s");
    model.sol("sol18").name("Parametric 18");

    model.batch("p1").set("control", "param");
    model.batch("p1").set("err", true);
    model.batch("p1")
         .set("plistarr", new String[]{"range(0.02 [ms],0.02 [ms],0.08 [ms])"});
    model.batch("p1").set("pname", new String[]{"t0"});
    model.batch("p1").set("control", "param");
    model.batch("p1").feature("so1").set("psol", "sol18");
    model.batch("p1").feature("so1")
         .set("param", new String[]{"\"t0\",\"1e-4\"", "\"t0\",\"1.2e-4\"", "\"t0\",\"1.4e-4\"", "\"t0\",\"1.6e-4\"", "\"t0\",\"1.8e-4\"", "\"t0\",\"2e-4\"", "\"t0\",\"2.2e-4\"", "\"t0\",\"2.4e-4\""});
    model.batch("p1").feature("so1").set("seq", "sol17");
    model.batch("p1").attach("std4");
    model.batch("p1").run();

    model.result().dataset("pw5_ds1").set("par1", "t");
    model.result().dataset("pw5_ds1").set("parmax1", "1.9999999999999998E-4");
    model.result().dataset("pw5_ds1").set("function", "all");
    model.result().dataset("pw5_ds2").set("par1", "t");
    model.result().dataset("pw5_ds2").set("parmax1", "0");
    model.result().dataset("pw5_ds2").set("function", "all");
    model.result().dataset("pw5_ds2")
         .set("parmin1", "-1.9999999999999998E-5");
    model.result().dataset("pw5_ds3").set("par1", "t");
    model.result().dataset("pw5_ds3").set("parmax1", "2.1999999999999998E-4");
    model.result().dataset("pw5_ds3").set("function", "all");
    model.result().dataset("pw5_ds3").set("parmin1", "1.9999999999999998E-4");
    model.result().dataset("pw3_ds1").set("par1", "t");
    model.result().dataset("pw3_ds1").set("parmax1", "9.999999999999999E-5");
    model.result().dataset("pw3_ds1").set("function", "all");
    model.result().dataset("pw3_ds2").set("par1", "t");
    model.result().dataset("pw3_ds2").set("parmax1", "0");
    model.result().dataset("pw3_ds2").set("function", "all");
    model.result().dataset("pw3_ds2").set("parmin1", "-9.999999999999999E-6");
    model.result().dataset("pw3_ds3").set("par1", "t");
    model.result().dataset("pw3_ds3").set("parmax1", "1.0999999999999999E-4");
    model.result().dataset("pw3_ds3").set("function", "all");
    model.result().dataset("pw3_ds3").set("parmin1", "9.999999999999999E-5");
    model.result().dataset("pw5_ds4").set("par1", "t");
    model.result().dataset("pw5_ds4").set("parmax1", "7.999999999999999E-5");
    model.result().dataset("pw5_ds4").set("function", "all");
    model.result().dataset("pw5_ds4").set("parmin1", "3.9999999999999996E-5");
    model.result().dataset("pw5_ds5").set("par1", "t");
    model.result().dataset("pw5_ds5").set("parmax1", "3.9999999999999996E-5");
    model.result().dataset("pw5_ds5").set("function", "all");
    model.result().dataset("pw5_ds5").set("parmin1", "3.5999999999999994E-5");
    model.result().dataset("pw5_ds6").set("par1", "t");
    model.result().dataset("pw5_ds6").set("parmax1", "8.4E-5");
    model.result().dataset("pw5_ds6").set("function", "all");
    model.result().dataset("pw5_ds6").set("parmin1", "7.999999999999999E-5");
    model.result().dataset("pw3_ds4").set("par1", "t");
    model.result().dataset("pw3_ds4").set("parmax1", "0.0010");
    model.result().dataset("pw3_ds4").set("function", "all");
    model.result().dataset("pw3_ds5").set("par1", "t");
    model.result().dataset("pw3_ds5").set("parmax1", "0");
    model.result().dataset("pw3_ds5").set("function", "all");
    model.result().dataset("pw3_ds5").set("parmin1", "-1.0E-4");
    model.result().dataset("pw3_ds6").set("par1", "t");
    model.result().dataset("pw3_ds6").set("parmax1", "0.0011");
    model.result().dataset("pw3_ds6").set("function", "all");
    model.result().dataset("pw3_ds6").set("parmin1", "0.0010");
    model.result().dataset("pw3_ds7").set("par1", "t");
    model.result().dataset("pw3_ds7").set("parmax1", "0.0050");
    model.result().dataset("pw3_ds7").set("function", "all");
    model.result().dataset("pw3_ds8").set("par1", "t");
    model.result().dataset("pw3_ds8").set("parmax1", "0");
    model.result().dataset("pw3_ds8").set("function", "all");
    model.result().dataset("pw3_ds8").set("parmin1", "-5.0E-4");
    model.result().dataset("pw3_ds9").set("par1", "t");
    model.result().dataset("pw3_ds9").set("parmax1", "0.0055");
    model.result().dataset("pw3_ds9").set("function", "all");
    model.result().dataset("pw3_ds9").set("parmin1", "0.0050");
    model.result("pg10").name("Pressure Plots");
    model.result("pg10").set("data", "dset6");
    model.result("pg10").set("ylabel", "-solid.u_ttZ (m/s<sup>2</sup>)");
    model.result("pg10").set("xlabel", "Time (\u00b5s)");
    model.result("pg10").set("xlabelactive", false);
    model.result("pg10").set("ylabelactive", false);
    model.result("pg10").feature("ptgr1").active(false);
    model.result("pg10").feature("ptgr1").name("Source");
    model.result("pg10").feature("ptgr1").set("expr", "solid.u_ttZ*1e-16");
    model.result("pg10").feature("ptgr1").set("unit", "m/s^2");
    model.result("pg10").feature("ptgr1").set("descr", "solid.u_ttZ*1e-16");
    model.result("pg10").feature("ptgr2").name("Microphone Acceleration");
    model.result("pg10").feature("ptgr2").set("expr", "-solid.u_ttZ");
    model.result("pg10").feature("ptgr2").set("unit", "m/s^2");
    model.result("pg10").feature("ptgr2").set("descr", "-solid.u_ttZ");
    model.result("pg10").feature("ptgr3").active(false);
    model.result("pg10").feature("ptgr3").name("Microphone  Pressure");
    model.result("pg10").feature("ptgr3").set("expr", "solid.pm*100");
    model.result("pg10").feature("ptgr3").set("unit", "N/m^2");
    model.result("pg10").feature("ptgr3").set("descr", "solid.pm*100");
    model.result("pg11").name("Stress (solid)");
    model.result("pg11").feature("surf1").set("expr", "solid.mises");
    model.result("pg11").feature("surf1").set("unit", "N/m^2");
    model.result("pg11").feature("surf1").set("descr", "von Mises stress");
    model.result("pg11").feature("surf1").feature("def")
         .set("scale", "195887.82404132673");
    model.result("pg11").feature("surf1").feature("def")
         .set("scaleactive", false);
    model.result("pg12").name("Stress (solid) 1");
    model.result("pg12").feature("surf1").set("expr", "solid.mises");
    model.result("pg12").feature("surf1").set("unit", "N/m^2");
    model.result("pg12").feature("surf1").set("descr", "von Mises stress");
    model.result("pg12").feature("surf1").feature("def")
         .set("scale", "396218.82927591325");
    model.result("pg12").feature("surf1").feature("def")
         .set("scaleactive", false);
    model.result("pg13").set("data", "none");
    model.result("pg13").set("ylabel", "Impact(t) (N)");
    model.result("pg13").set("xlabel", "t (s)");
    model.result("pg13").set("xlabelactive", true);
    model.result("pg13").set("title", "Impact(t) (N)");
    model.result("pg13").set("titletype", "manual");
    model.result("pg13").set("ylabelactive", true);
    model.result("pg13").feature("plot1").set("data", "pw3_ds4");
    model.result("pg13").feature("plot1").set("solrepresentation", "solnum");
    model.result("pg13").feature("plot1").set("unit", "");
    model.result("pg13").feature("plot1").set("xdataunit", "");
    model.result("pg13").feature("plot1").set("xdata", "expr");
    model.result("pg13").feature("plot1")
         .set("expr", "mod1.Impact(root.t[s])");
    model.result("pg13").feature("plot1").set("xdataexpr", "root.t");
    model.result("pg13").feature("plot1").set("descr", "Impact(t)");
    model.result("pg13").feature("plot1")
         .set("const", new String[][]{{"mod1.solid.refpntx", "0", "Reference point for moment computation, x coordinate"}, {"mod1.solid.refpnty", "0", "Reference point for moment computation, y coordinate"}, {"mod1.solid.refpntz", "0", "Reference point for moment computation, z coordinate"}});
    model.result("pg13").feature("plot1").set("xdatadescr", "root.t");
    model.result("pg13").feature("plot1").set("xdataunit", "");
    model.result("pg13").feature("plot2").name("Left Extrapolation");
    model.result("pg13").feature("plot2").set("data", "pw3_ds5");
    model.result("pg13").feature("plot2").set("linecolor", "red");
    model.result("pg13").feature("plot2").set("solrepresentation", "solnum");
    model.result("pg13").feature("plot2").set("unit", "");
    model.result("pg13").feature("plot2").set("xdataunit", "");
    model.result("pg13").feature("plot2").set("xdata", "expr");
    model.result("pg13").feature("plot2")
         .set("expr", "mod1.Impact(root.t[s])");
    model.result("pg13").feature("plot2").set("xdataexpr", "root.t");
    model.result("pg13").feature("plot2")
         .set("descr", "mod1.Impact(root.t[s])");
    model.result("pg13").feature("plot2").set("linestyle", "dashed");
    model.result("pg13").feature("plot2")
         .set("const", new String[][]{{"mod1.solid.refpntx", "0", "Reference point for moment computation, x coordinate"}, {"mod1.solid.refpnty", "0", "Reference point for moment computation, y coordinate"}, {"mod1.solid.refpntz", "0", "Reference point for moment computation, z coordinate"}});
    model.result("pg13").feature("plot2").set("xdatadescr", "root.t");
    model.result("pg13").feature("plot2").set("xdataunit", "");
    model.result("pg13").feature("plot3").name("Right Extrapolation");
    model.result("pg13").feature("plot3").set("data", "pw3_ds6");
    model.result("pg13").feature("plot3").set("linecolor", "red");
    model.result("pg13").feature("plot3").set("solrepresentation", "solnum");
    model.result("pg13").feature("plot3").set("unit", "");
    model.result("pg13").feature("plot3").set("xdataunit", "");
    model.result("pg13").feature("plot3").set("xdata", "expr");
    model.result("pg13").feature("plot3")
         .set("expr", "mod1.Impact(root.t[s])");
    model.result("pg13").feature("plot3").set("xdataexpr", "root.t");
    model.result("pg13").feature("plot3")
         .set("descr", "mod1.Impact(root.t[s])");
    model.result("pg13").feature("plot3").set("linestyle", "dashed");
    model.result("pg13").feature("plot3")
         .set("const", new String[][]{{"mod1.solid.refpntx", "0", "Reference point for moment computation, x coordinate"}, {"mod1.solid.refpnty", "0", "Reference point for moment computation, y coordinate"}, {"mod1.solid.refpntz", "0", "Reference point for moment computation, z coordinate"}});
    model.result("pg13").feature("plot3").set("xdatadescr", "root.t");
    model.result("pg13").feature("plot3").set("xdataunit", "");
    model.result("pg14").set("data", "none");
    model.result("pg14").set("ylabel", "Impact(t) (N)");
    model.result("pg14").set("xlabel", "t (s)");
    model.result("pg14").set("xlabelactive", true);
    model.result("pg14").set("title", "Impact(t) (N)");
    model.result("pg14").set("titletype", "manual");
    model.result("pg14").set("ylabelactive", true);
    model.result("pg14").feature("plot1").set("data", "pw3_ds7");
    model.result("pg14").feature("plot1").set("solrepresentation", "solnum");
    model.result("pg14").feature("plot1").set("unit", "");
    model.result("pg14").feature("plot1").set("xdataunit", "");
    model.result("pg14").feature("plot1").set("xdata", "expr");
    model.result("pg14").feature("plot1")
         .set("expr", "mod1.Impact(root.t[s])");
    model.result("pg14").feature("plot1").set("xdataexpr", "root.t");
    model.result("pg14").feature("plot1").set("descr", "Impact(t)");
    model.result("pg14").feature("plot1")
         .set("const", new String[][]{{"mod1.solid.refpntx", "0", "Reference point for moment computation, x coordinate"}, {"mod1.solid.refpnty", "0", "Reference point for moment computation, y coordinate"}, {"mod1.solid.refpntz", "0", "Reference point for moment computation, z coordinate"}});
    model.result("pg14").feature("plot1").set("xdatadescr", "Time");
    model.result("pg14").feature("plot1").set("xdataunit", "");
    model.result("pg14").feature("plot2").name("Left Extrapolation");
    model.result("pg14").feature("plot2").set("data", "pw3_ds8");
    model.result("pg14").feature("plot2").set("linecolor", "red");
    model.result("pg14").feature("plot2").set("solrepresentation", "solnum");
    model.result("pg14").feature("plot2").set("unit", "");
    model.result("pg14").feature("plot2").set("xdataunit", "");
    model.result("pg14").feature("plot2").set("xdata", "expr");
    model.result("pg14").feature("plot2")
         .set("expr", "mod1.Impact(root.t[s])");
    model.result("pg14").feature("plot2").set("xdataexpr", "root.t");
    model.result("pg14").feature("plot2")
         .set("descr", "mod1.Impact(root.t[s])");
    model.result("pg14").feature("plot2").set("linestyle", "dashed");
    model.result("pg14").feature("plot2")
         .set("const", new String[][]{{"mod1.solid.refpntx", "0", "Reference point for moment computation, x coordinate"}, {"mod1.solid.refpnty", "0", "Reference point for moment computation, y coordinate"}, {"mod1.solid.refpntz", "0", "Reference point for moment computation, z coordinate"}});
    model.result("pg14").feature("plot2").set("xdatadescr", "Time");
    model.result("pg14").feature("plot2").set("xdataunit", "");
    model.result("pg14").feature("plot3").name("Right Extrapolation");
    model.result("pg14").feature("plot3").set("data", "pw3_ds9");
    model.result("pg14").feature("plot3").set("linecolor", "red");
    model.result("pg14").feature("plot3").set("solrepresentation", "solnum");
    model.result("pg14").feature("plot3").set("unit", "");
    model.result("pg14").feature("plot3").set("xdataunit", "");
    model.result("pg14").feature("plot3").set("xdata", "expr");
    model.result("pg14").feature("plot3")
         .set("expr", "mod1.Impact(root.t[s])");
    model.result("pg14").feature("plot3").set("xdataexpr", "root.t");
    model.result("pg14").feature("plot3")
         .set("descr", "mod1.Impact(root.t[s])");
    model.result("pg14").feature("plot3").set("linestyle", "dashed");
    model.result("pg14").feature("plot3")
         .set("const", new String[][]{{"mod1.solid.refpntx", "0", "Reference point for moment computation, x coordinate"}, {"mod1.solid.refpnty", "0", "Reference point for moment computation, y coordinate"}, {"mod1.solid.refpntz", "0", "Reference point for moment computation, z coordinate"}});
    model.result("pg14").feature("plot3").set("xdatadescr", "Time");
    model.result("pg14").feature("plot3").set("xdataunit", "");
    model.result("pg15").name("Mode Shape (solid)");
    model.result("pg15").set("data", "dset4");
    model.result("pg15").feature("surf1").feature("def")
         .set("scale", "0.004951629035251683");
    model.result("pg15").feature("surf1").feature("def")
         .set("scaleactive", false);
    model.result("pg15").feature("arwl1")
         .set("scale", "0.005740454667248107");
    model.result("pg15").feature("arwl1").set("scaleactive", false);
    model.result("pg16").name("Stress (solid) 2");
    model.result("pg16").set("data", "dset6");
    model.result("pg16").feature("surf1").set("expr", "solid.mises");
    model.result("pg16").feature("surf1").set("unit", "N/m^2");
    model.result("pg16").feature("surf1").set("descr", "von Mises stress");
    model.result().export("plot1").name("Microphone");
    model.result().export("plot1")
         .set("filename", "C:\\Users\\plane\\Desktop\\Data\\COMSOL\\Al Cavity\\Ring and Disk Validity Test\\disk_on_ring_0.24ms_24cm_sep_25ms_1e15_1e15_1e15.csv");
    model.result().export("plot1").set("plot", "ptgr2");
    model.result().export("plot1").set("sort", true);

    return model;
  }

}
