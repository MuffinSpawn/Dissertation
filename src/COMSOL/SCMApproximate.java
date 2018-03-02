/*
 * SCMApproximate.java
 */

import com.comsol.model.*;
import com.comsol.model.util.*;

/** Model exported on Jan 17 2016, 15:35 by COMSOL 4.4.0.195. */
public class SCMApproximate {

  public static void main(String[] args) {
    run();
  }

  public static Model run() {
    Model model = ModelUtil.create("Model");

    model.modelPath("C:\\Users\\plane\\Desktop\\COMSOL");

    model.name("SCM_Approximate.mph");

    model.param().set("OuterRadius", "60 [cm]");
    model.param().set("InnerRadius", "59.5 [cm]");
    model.param().set("OuterHeight", "41 [cm]");
    model.param().set("InnerHeight", "38.2 [cm]");
    model.param().set("SourceRadius", "1.0 [mm]");
    model.param().set("SourceHeight", "4.0 [mm]");
    model.param().set("SourceRho", "5.0 [cm]");
    model.param().set("SourceTheta", "0 [deg]");
    model.param().set("SourceX", "SourceRho * cos(SourceTheta)");
    model.param().set("SourceY", "SourceRho * sin(SourceTheta)");
    model.param().set("t0", "200 [us]", "Spark duration");
    model.param().set("gamma", "10", "Spark decay rate");
    model.param().set("F0", "100 [N]", "Spark force magnitude");

    model.modelNode().create("comp1");

    model.func().create("pw1", "Piecewise");
    model.func("pw1").model("comp1");
    model.func("pw1")
         .set("pieces", new String[][]{{"0", "t0", "F0*exp(-gamma/t0*t)*sin(4*pi/(1+t0/t))"}});
    model.func("pw1").set("argunit", "s");
    model.func("pw1").set("fununit", "N");
    model.func("pw1").set("funcname", "Spark");
    model.func("pw1").set("arg", "t");

    model.geom().create("geom1", 3);
    model.geom("geom1").feature().create("cyl1", "Cylinder");
    model.geom("geom1").feature().create("cyl2", "Cylinder");
    model.geom("geom1").feature().create("dif1", "Difference");
    model.geom("geom1").feature().create("cyl5", "Cylinder");
    model.geom("geom1").feature().create("cyl6", "Cylinder");
    model.geom("geom1").feature().create("par1", "Partition");
    model.geom("geom1").feature().create("cyl3", "Cylinder");
    model.geom("geom1").feature().create("cyl4", "Cylinder");
    model.geom("geom1").feature().create("pt1", "Point");
    model.geom("geom1").feature().create("pt2", "Point");
    model.geom("geom1").feature().create("pt3", "Point");
    model.geom("geom1").feature().create("pt4", "Point");
    model.geom("geom1").feature().create("pt5", "Point");
    model.geom("geom1").feature().create("pt6", "Point");
    model.geom("geom1").feature().create("pt7", "Point");
    model.geom("geom1").feature().create("pt8", "Point");
    model.geom("geom1").feature().create("pt9", "Point");
    model.geom("geom1").feature().create("pt10", "Point");
    model.geom("geom1").feature().create("pt11", "Point");
    model.geom("geom1").feature().create("pt12", "Point");
    model.geom("geom1").feature().create("pt13", "Point");
    model.geom("geom1").feature().create("pt14", "Point");
    model.geom("geom1").feature().create("pt15", "Point");
    model.geom("geom1").feature().create("pt16", "Point");
    model.geom("geom1").feature().create("pt17", "Point");
    model.geom("geom1").feature().create("pt18", "Point");
    model.geom("geom1").feature().create("pt19", "Point");
    model.geom("geom1").feature().create("pt20", "Point");
    model.geom("geom1").feature().create("pt21", "Point");
    model.geom("geom1").feature().create("pt22", "Point");
    model.geom("geom1").feature().create("pt23", "Point");
    model.geom("geom1").feature().create("pt24", "Point");
    model.geom("geom1").feature("cyl1").set("r", "OuterRadius");
    model.geom("geom1").feature("cyl1")
         .set("pos", new String[]{"0", "0", "-OuterHeight/2"});
    model.geom("geom1").feature("cyl1").set("h", "OuterHeight");
    model.geom("geom1").feature("cyl2").set("r", "InnerRadius");
    model.geom("geom1").feature("cyl2")
         .set("pos", new String[]{"0", "0", "-InnerHeight/2"});
    model.geom("geom1").feature("cyl2").set("h", "InnerHeight");
    model.geom("geom1").feature("dif1").selection("input")
         .set(new String[]{"cyl1"});
    model.geom("geom1").feature("dif1").selection("input2")
         .set(new String[]{"cyl2"});
    model.geom("geom1").feature("cyl5").name("Upstream Source Region");
    model.geom("geom1").feature("cyl5").set("r", "1 [cm]");
    model.geom("geom1").feature("cyl5")
         .set("pos", new String[]{"SourceX", "SourceY", "-OuterHeight/2"});
    model.geom("geom1").feature("cyl5")
         .set("h", "OuterHeight/2-InnerHeight/2");
    model.geom("geom1").feature("cyl6").name("Downstream Source Region");
    model.geom("geom1").feature("cyl6").set("r", "1 [cm]");
    model.geom("geom1").feature("cyl6")
         .set("pos", new String[]{"SourceX", "SourceY", "InnerHeight/2"});
    model.geom("geom1").feature("cyl6")
         .set("h", "OuterHeight/2-InnerHeight/2");
    model.geom("geom1").feature("par1").name("Source Regions");
    model.geom("geom1").feature("par1").selection("tool")
         .set(new String[]{"cyl5", "cyl6"});
    model.geom("geom1").feature("par1").selection("input")
         .set(new String[]{"dif1"});
    model.geom("geom1").feature("cyl3").name("Upstream Source");
    model.geom("geom1").feature("cyl3").set("r", "SourceRadius");
    model.geom("geom1").feature("cyl3")
         .set("pos", new String[]{"SourceX", "SourceY", "-InnerHeight/2.0-SourceHeight"});
    model.geom("geom1").feature("cyl3").set("h", "SourceHeight");
    model.geom("geom1").feature("cyl4").name("Downstream Source");
    model.geom("geom1").feature("cyl4").set("r", "SourceRadius");
    model.geom("geom1").feature("cyl4")
         .set("pos", new String[]{"SourceX", "SourceY", "InnerHeight/2.0"});
    model.geom("geom1").feature("cyl4").set("h", "SourceHeight");
    model.geom("geom1").feature("pt1").name("UB1");
    model.geom("geom1").feature("pt1").setIndex("p", "-20.5 [cm]", 0, 0);
    model.geom("geom1").feature("pt1").setIndex("p", "-35.5 [cm]", 1, 0);
    model.geom("geom1").feature("pt1")
         .setIndex("p", "-(OuterHeight/2)", 2, 0);
    model.geom("geom1").feature("pt2").name("UB2");
    model.geom("geom1").feature("pt2").setIndex("p", "20.5 [cm]", 0, 0);
    model.geom("geom1").feature("pt2").setIndex("p", "-35.5 [cm]", 1, 0);
    model.geom("geom1").feature("pt2")
         .setIndex("p", "-(OuterHeight/2)", 2, 0);
    model.geom("geom1").feature("pt3").name("UB3");
    model.geom("geom1").feature("pt3").setIndex("p", "41 [cm]", 0, 0);
    model.geom("geom1").feature("pt3").setIndex("p", "0 [cm]", 1, 0);
    model.geom("geom1").feature("pt3")
         .setIndex("p", "-(OuterHeight/2)", 2, 0);
    model.geom("geom1").feature("pt4").name("UB4");
    model.geom("geom1").feature("pt4").setIndex("p", "20.5 [cm]", 0, 0);
    model.geom("geom1").feature("pt4").setIndex("p", "35.5 [cm]", 1, 0);
    model.geom("geom1").feature("pt4")
         .setIndex("p", "-(OuterHeight/2)", 2, 0);
    model.geom("geom1").feature("pt5").name("UB5");
    model.geom("geom1").feature("pt5").setIndex("p", "-20.5 [cm]", 0, 0);
    model.geom("geom1").feature("pt5").setIndex("p", "35.5 [cm]", 1, 0);
    model.geom("geom1").feature("pt5")
         .setIndex("p", "-(OuterHeight/2)", 2, 0);
    model.geom("geom1").feature("pt6").name("UB6");
    model.geom("geom1").feature("pt6").setIndex("p", "-41 [cm]", 0, 0);
    model.geom("geom1").feature("pt6").setIndex("p", "0 [cm]", 1, 0);
    model.geom("geom1").feature("pt6")
         .setIndex("p", "-(OuterHeight/2)", 2, 0);
    model.geom("geom1").feature("pt7").name("UW1");
    model.geom("geom1").feature("pt7").setIndex("p", "0 [cm]", 0, 0);
    model.geom("geom1").feature("pt7").setIndex("p", "-11.43 [cm]", 1, 0);
    model.geom("geom1").feature("pt7")
         .setIndex("p", "-(OuterHeight/2)", 2, 0);
    model.geom("geom1").feature("pt8").name("UW2");
    model.geom("geom1").feature("pt8").setIndex("p", "10.9 [cm]", 0, 0);
    model.geom("geom1").feature("pt8").setIndex("p", "-3.53 [cm]", 1, 0);
    model.geom("geom1").feature("pt8")
         .setIndex("p", "-(OuterHeight/2)", 2, 0);
    model.geom("geom1").feature("pt9").name("UW3");
    model.geom("geom1").feature("pt9").setIndex("p", "6.72 [cm]", 0, 0);
    model.geom("geom1").feature("pt9").setIndex("p", "9.25 [cm]", 1, 0);
    model.geom("geom1").feature("pt9")
         .setIndex("p", "-(OuterHeight/2)", 2, 0);
    model.geom("geom1").feature("pt10").name("UW4");
    model.geom("geom1").feature("pt10").setIndex("p", "-6.72 [cm]", 0, 0);
    model.geom("geom1").feature("pt10").setIndex("p", "9.25 [cm]", 1, 0);
    model.geom("geom1").feature("pt10")
         .setIndex("p", "-(OuterHeight/2)", 2, 0);
    model.geom("geom1").feature("pt11").name("UW5");
    model.geom("geom1").feature("pt11").setIndex("p", "-10.9 [cm]", 0, 0);
    model.geom("geom1").feature("pt11").setIndex("p", "-3.53 [cm]", 1, 0);
    model.geom("geom1").feature("pt11")
         .setIndex("p", "-(OuterHeight/2)", 2, 0);
    model.geom("geom1").feature("pt12").name("UW6");
    model.geom("geom1").feature("pt12").setIndex("p", "0. [cm]", 0, 0);
    model.geom("geom1").feature("pt12").setIndex("p", "0. [cm]", 1, 0);
    model.geom("geom1").feature("pt12")
         .setIndex("p", "-(OuterHeight/2)", 2, 0);
    model.geom("geom1").feature("pt13").name("DB1");
    model.geom("geom1").feature("pt13").setIndex("p", "-20.5 [cm]", 0, 0);
    model.geom("geom1").feature("pt13").setIndex("p", "-35.5 [cm]", 1, 0);
    model.geom("geom1").feature("pt13")
         .setIndex("p", "(OuterHeight/2)", 2, 0);
    model.geom("geom1").feature("pt14").name("DB2");
    model.geom("geom1").feature("pt14").setIndex("p", "20.5 [cm]", 0, 0);
    model.geom("geom1").feature("pt14").setIndex("p", "-35.5 [cm]", 1, 0);
    model.geom("geom1").feature("pt14")
         .setIndex("p", "(OuterHeight/2)", 2, 0);
    model.geom("geom1").feature("pt15").name("DB3");
    model.geom("geom1").feature("pt15").setIndex("p", "41 [cm]", 0, 0);
    model.geom("geom1").feature("pt15").setIndex("p", "0 [cm]", 1, 0);
    model.geom("geom1").feature("pt15")
         .setIndex("p", "(OuterHeight/2)", 2, 0);
    model.geom("geom1").feature("pt16").name("DB4");
    model.geom("geom1").feature("pt16").setIndex("p", "20.5 [cm]", 0, 0);
    model.geom("geom1").feature("pt16").setIndex("p", "35.5 [cm]", 1, 0);
    model.geom("geom1").feature("pt16")
         .setIndex("p", "(OuterHeight/2)", 2, 0);
    model.geom("geom1").feature("pt17").name("DB5");
    model.geom("geom1").feature("pt17").setIndex("p", "-20.5 [cm]", 0, 0);
    model.geom("geom1").feature("pt17").setIndex("p", "35.5 [cm]", 1, 0);
    model.geom("geom1").feature("pt17")
         .setIndex("p", "(OuterHeight/2)", 2, 0);
    model.geom("geom1").feature("pt18").name("DB6");
    model.geom("geom1").feature("pt18").setIndex("p", "-41 [cm]", 0, 0);
    model.geom("geom1").feature("pt18").setIndex("p", "0 [cm]", 1, 0);
    model.geom("geom1").feature("pt18")
         .setIndex("p", "(OuterHeight/2)", 2, 0);
    model.geom("geom1").feature("pt19").name("DW1");
    model.geom("geom1").feature("pt19").setIndex("p", "0 [cm]", 0, 0);
    model.geom("geom1").feature("pt19").setIndex("p", "-11.43 [cm]", 1, 0);
    model.geom("geom1").feature("pt19")
         .setIndex("p", "(OuterHeight/2)", 2, 0);
    model.geom("geom1").feature("pt20").name("DW2");
    model.geom("geom1").feature("pt20").setIndex("p", "10.9 [cm]", 0, 0);
    model.geom("geom1").feature("pt20").setIndex("p", "-3.53 [cm]", 1, 0);
    model.geom("geom1").feature("pt20")
         .setIndex("p", "(OuterHeight/2)", 2, 0);
    model.geom("geom1").feature("pt21").name("DW3");
    model.geom("geom1").feature("pt21").setIndex("p", "6.72 [cm]", 0, 0);
    model.geom("geom1").feature("pt21").setIndex("p", "9.25 [cm]", 1, 0);
    model.geom("geom1").feature("pt21")
         .setIndex("p", "(OuterHeight/2)", 2, 0);
    model.geom("geom1").feature("pt22").name("DW4");
    model.geom("geom1").feature("pt22").setIndex("p", "-6.72 [cm]", 0, 0);
    model.geom("geom1").feature("pt22").setIndex("p", "9.25 [cm]", 1, 0);
    model.geom("geom1").feature("pt22")
         .setIndex("p", "(OuterHeight/2)", 2, 0);
    model.geom("geom1").feature("pt23").name("DW5");
    model.geom("geom1").feature("pt23").setIndex("p", "-10.9 [cm]", 0, 0);
    model.geom("geom1").feature("pt23").setIndex("p", "-3.53 [cm]", 1, 0);
    model.geom("geom1").feature("pt23")
         .setIndex("p", "(OuterHeight/2)", 2, 0);
    model.geom("geom1").feature("pt24").name("DW6");
    model.geom("geom1").feature("pt24").setIndex("p", "0. [cm]", 0, 0);
    model.geom("geom1").feature("pt24").setIndex("p", "0. [cm]", 1, 0);
    model.geom("geom1").feature("pt24")
         .setIndex("p", "(OuterHeight/2)", 2, 0);
    model.geom("geom1").run();

    model.selection().create("sel1", "Explicit");
    model.selection("sel1").geom("geom1", 0);
    model.selection("sel1").name("pt1");
    model.selection("sel1").name("Explicit 1");

    model.material().create("mat1");
    model.material("mat1").propertyGroup()
         .create("Enu", "Young's modulus and Poisson's ratio");
    model.material("mat1").propertyGroup()
         .create("linzRes", "Linearized resistivity");

    model.physics().create("solid", "SolidMechanics", "geom1");
    model.physics("solid").feature().create("bndl1", "BoundaryLoad", 2);
    model.physics("solid").feature("bndl1").selection()
         .set(new int[]{21, 22, 25, 26, 31, 32, 33, 34});

    model.mesh().create("mesh1", "geom1");
    model.mesh("mesh1").feature().create("ftet2", "FreeTet");
    model.mesh("mesh1").feature("ftet2").feature().create("size2", "Size");
    model.mesh("mesh1").feature("ftet2").feature().create("size1", "Size");
    model.mesh("mesh1").feature("ftet2").feature("size1").selection()
         .geom("geom1", 3);
    model.mesh("mesh1").feature("ftet2").feature("size1").selection()
         .set(new int[]{4, 5});

    model.result().table().create("evl3", "Table");

    model.view("view1").set("transparency", "on");

    model.material("mat1").name("Copper");
    model.material("mat1").propertyGroup("def")
         .set("relpermeability", new String[]{"1", "0", "0", "0", "1", "0", "0", "0", "1"});
    model.material("mat1").propertyGroup("def")
         .set("electricconductivity", new String[]{"5.998e7[S/m]", "0", "0", "0", "5.998e7[S/m]", "0", "0", "0", "5.998e7[S/m]"});
    model.material("mat1").propertyGroup("def")
         .set("thermalexpansioncoefficient", new String[]{"17e-6[1/K]", "0", "0", "0", "17e-6[1/K]", "0", "0", "0", "17e-6[1/K]"});
    model.material("mat1").propertyGroup("def")
         .set("heatcapacity", "385[J/(kg*K)]");
    model.material("mat1").propertyGroup("def")
         .set("relpermittivity", new String[]{"1", "0", "0", "0", "1", "0", "0", "0", "1"});
    model.material("mat1").propertyGroup("def")
         .set("density", "8700[kg/m^3]");
    model.material("mat1").propertyGroup("def")
         .set("thermalconductivity", new String[]{"400[W/(m*K)]", "0", "0", "0", "400[W/(m*K)]", "0", "0", "0", "400[W/(m*K)]"});
    model.material("mat1").propertyGroup("def").set("soundspeed", "3901");
    model.material("mat1").propertyGroup("Enu")
         .set("youngsmodulus", "110e9[Pa]");
    model.material("mat1").propertyGroup("Enu").set("poissonsratio", "0.35");
    model.material("mat1").propertyGroup("linzRes").set("rho0", "");
    model.material("mat1").propertyGroup("linzRes").set("alpha", "");
    model.material("mat1").propertyGroup("linzRes").set("Tref", "");
    model.material("mat1").propertyGroup("linzRes")
         .set("rho0", "1.72e-8[ohm*m]");
    model.material("mat1").propertyGroup("linzRes")
         .set("alpha", "0.0039[1/K]");
    model.material("mat1").propertyGroup("linzRes").set("Tref", "298[K]");
    model.material("mat1").propertyGroup("linzRes").addInput("temperature");

    model.physics("solid").feature("bndl1").set("LoadType", "TotalForce");
    model.physics("solid").feature("bndl1").set("coordinateSystem", "sys1");
    model.physics("solid").feature("bndl1")
         .set("Ftot", new String[][]{{"0"}, {"0"}, {"Spark(t)"}});

    model.mesh("mesh1").feature("ftet2").name("Free Tetrahedral");
    model.mesh("mesh1").feature("ftet2").feature("size2")
         .name("Source Interface");
    model.mesh("mesh1").feature("ftet2").feature("size2").set("hauto", 4);
    model.mesh("mesh1").feature("ftet2").feature("size1").name("Sources");
    model.mesh("mesh1").feature("ftet2").feature("size1").set("hauto", 1);
    model.mesh("mesh1").run();

    model.result().table("evl3").name("Evaluation 3D");
    model.result().table("evl3").comments("Interactive 3D values");

    model.study().create("std1");
    model.study("std1").feature().create("param", "Parametric");
    model.study("std1").feature().create("time", "Transient");

    model.sol().create("sol1");
    model.sol("sol1").study("std1");
    model.sol("sol1").attach("std1");
    model.sol("sol1").feature().create("st1", "StudyStep");
    model.sol("sol1").feature().create("v1", "Variables");
    model.sol("sol1").feature().create("t1", "Time");
    model.sol("sol1").feature("t1").feature().create("fc1", "FullyCoupled");
    model.sol("sol1").feature("t1").feature().remove("fcDef");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol().create("sol2");
    model.sol("sol2").study("std1");
    model.sol("sol2").feature().create("su1", "StoreSolution");
    model.sol("sol2").feature().create("su2", "StoreSolution");
    model.sol("sol2").feature().create("su3", "StoreSolution");
    model.sol("sol2").feature().create("su4", "StoreSolution");
    model.sol("sol2").feature().create("su5", "StoreSolution");
    model.sol("sol2").feature().create("su6", "StoreSolution");
    model.sol("sol2").feature().create("su7", "StoreSolution");
    model.sol("sol2").feature().create("su8", "StoreSolution");
    model.sol("sol2").feature().create("su9", "StoreSolution");
    model.sol("sol2").feature().create("su10", "StoreSolution");
    model.sol("sol2").feature().create("su11", "StoreSolution");
    model.sol("sol2").feature().create("su12", "StoreSolution");
    model.sol("sol2").feature().create("su13", "StoreSolution");
    model.sol("sol2").feature().create("su14", "StoreSolution");
    model.sol("sol2").feature().create("su15", "StoreSolution");
    model.sol("sol2").feature().create("su16", "StoreSolution");
    model.sol("sol2").feature().create("su17", "StoreSolution");
    model.sol("sol2").feature().create("su18", "StoreSolution");
    model.sol("sol2").feature().create("su19", "StoreSolution");
    model.sol("sol2").feature().create("su20", "StoreSolution");
    model.sol("sol2").feature().create("su21", "StoreSolution");
    model.sol("sol2").feature().create("su22", "StoreSolution");
    model.sol("sol2").feature().create("su23", "StoreSolution");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol3").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol4").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol5").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol6").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol7").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol8").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol9").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol10").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol11").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol12").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol13").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol14").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol15").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol16").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol17").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol18").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol19").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol20").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol21").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol22").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol23").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol24").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol25").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol().create("sol26");
    model.sol("sol26").study("std1");
    model.sol("sol26").feature().create("su1", "StoreSolution");
    model.sol("sol26").feature().create("su2", "StoreSolution");
    model.sol("sol26").feature().create("su3", "StoreSolution");
    model.sol("sol26").feature().create("su4", "StoreSolution");
    model.sol("sol26").feature().create("su5", "StoreSolution");
    model.sol("sol26").feature().create("su6", "StoreSolution");
    model.sol("sol26").feature().create("su7", "StoreSolution");
    model.sol("sol26").feature().create("su8", "StoreSolution");
    model.sol("sol26").feature().create("su9", "StoreSolution");
    model.sol("sol26").feature().create("su10", "StoreSolution");
    model.sol("sol26").feature().create("su11", "StoreSolution");
    model.sol("sol26").feature().create("su12", "StoreSolution");
    model.sol("sol26").feature().create("su13", "StoreSolution");
    model.sol("sol26").feature().create("su14", "StoreSolution");
    model.sol("sol26").feature().create("su15", "StoreSolution");
    model.sol("sol26").feature().create("su16", "StoreSolution");
    model.sol("sol26").feature().create("su17", "StoreSolution");
    model.sol("sol26").feature().create("su18", "StoreSolution");
    model.sol("sol26").feature().create("su19", "StoreSolution");
    model.sol("sol26").feature().create("su20", "StoreSolution");
    model.sol("sol26").feature().create("su21", "StoreSolution");
    model.sol("sol26").feature().create("su22", "StoreSolution");
    model.sol("sol26").feature().create("su23", "StoreSolution");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol27").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol28").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol29").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol30").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol31").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol32").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol33").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol34").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol35").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol36").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol37").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol38").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol39").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol40").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol41").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol42").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol43").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol44").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol45").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol46").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol47").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol48").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol49").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol().create("sol50");
    model.sol("sol50").study("std1");
    model.sol("sol50").feature().create("su1", "StoreSolution");
    model.sol("sol50").feature().create("su2", "StoreSolution");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol51").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol52").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol().create("sol53");
    model.sol("sol53").study("std1");
    model.sol("sol53").feature().create("su1", "StoreSolution");
    model.sol("sol53").feature().create("su2", "StoreSolution");
    model.sol("sol53").feature().create("su3", "StoreSolution");
    model.sol("sol53").feature().create("su4", "StoreSolution");
    model.sol("sol53").feature().create("su5", "StoreSolution");
    model.sol("sol53").feature().create("su6", "StoreSolution");
    model.sol("sol53").feature().create("su7", "StoreSolution");
    model.sol("sol53").feature().create("su8", "StoreSolution");
    model.sol("sol53").feature().create("su9", "StoreSolution");
    model.sol("sol53").feature().create("su10", "StoreSolution");
    model.sol("sol53").feature().create("su11", "StoreSolution");
    model.sol("sol53").feature().create("su12", "StoreSolution");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol54").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol55").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol56").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol57").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol58").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol59").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol60").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol61").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol62").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol63").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol64").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.sol("sol65").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");

    model.batch().create("p1", "Parametric");
    model.batch("p1").feature().create("so1", "Solutionseq");
    model.batch("p1").study("std1");

    model.result().dataset("dset5").set("solution", "sol1");
    model.result().dataset("dset6").set("solution", "sol53");
    model.result().dataset("dset7").set("solution", "sol53");
    model.result().dataset().remove("dset1");
    model.result().dataset().remove("dset2");
    model.result().dataset().remove("dset3");
    model.result().dataset().remove("dset4");
    model.result().dataset().remove("dset8");
    model.result().dataset().remove("dset9");
    model.result().dataset().remove("dset10");
    model.result().dataset().remove("dset11");
    model.result().dataset().remove("dset12");
    model.result().dataset().remove("dset13");
    model.result().dataset().remove("dset14");
    model.result().dataset().remove("dset15");
    model.result().dataset().remove("dset16");
    model.result().dataset().remove("dset17");
    model.result().dataset().remove("dset18");
    model.result().dataset().remove("dset19");
    model.result().dataset().remove("dset20");
    model.result().dataset().remove("dset21");
    model.result().dataset().remove("dset22");
    model.result().dataset().remove("dset23");
    model.result().dataset().remove("dset24");
    model.result().dataset().remove("dset25");
    model.result().dataset().remove("dset26");
    model.result().dataset().remove("dset27");
    model.result().dataset().remove("dset28");
    model.result().dataset().remove("dset29");
    model.result().dataset().remove("dset30");
    model.result().dataset().remove("dset31");
    model.result().dataset().remove("dset32");
    model.result().dataset().remove("dset33");
    model.result().dataset().remove("dset34");
    model.result().dataset().remove("dset35");
    model.result().dataset().remove("dset36");
    model.result().dataset().remove("dset37");
    model.result().dataset().remove("dset38");
    model.result().dataset().remove("dset39");
    model.result().dataset().remove("dset40");
    model.result().dataset().remove("dset41");
    model.result().dataset().remove("dset42");
    model.result().dataset().remove("dset43");
    model.result().dataset().remove("dset44");
    model.result().dataset().remove("dset45");
    model.result().dataset().remove("dset46");
    model.result().dataset().remove("dset47");
    model.result().dataset().remove("dset48");
    model.result().dataset().remove("dset49");
    model.result().dataset().remove("dset50");
    model.result().dataset().remove("dset51");
    model.result().dataset().remove("dset52");
    model.result().dataset().remove("dset53");
    model.result().dataset().remove("dset54");
    model.result().dataset().remove("dset55");
    model.result().dataset().remove("dset56");
    model.result().dataset().remove("dset57");
    model.result().dataset().remove("dset58");
    model.result().dataset().remove("dset59");
    model.result().dataset().remove("dset60");
    model.result().dataset().remove("dset61");
    model.result().dataset().remove("dset62");
    model.result().dataset().remove("dset63");
    model.result().dataset().remove("dset64");
    model.result().dataset().remove("dset65");
    model.result().create("pg3", "PlotGroup1D");
    model.result().create("pg4", "PlotGroup1D");
    model.result("pg3").feature().create("ptgr1", "PointGraph");
    model.result("pg3").feature().create("ptgr2", "PointGraph");
    model.result("pg3").feature().create("ptgr3", "PointGraph");
    model.result("pg3").feature().create("ptgr4", "PointGraph");
    model.result("pg3").feature().create("ptgr5", "PointGraph");
    model.result("pg3").feature().create("ptgr6", "PointGraph");
    model.result("pg3").feature().create("ptgr7", "PointGraph");
    model.result("pg3").feature().create("ptgr8", "PointGraph");
    model.result("pg3").feature().create("ptgr9", "PointGraph");
    model.result("pg3").feature().create("ptgr10", "PointGraph");
    model.result("pg3").feature().create("ptgr11", "PointGraph");
    model.result("pg3").feature().create("ptgr12", "PointGraph");
    model.result("pg3").feature().create("ptgr13", "PointGraph");
    model.result("pg3").feature().create("ptgr14", "PointGraph");
    model.result("pg3").feature().create("ptgr15", "PointGraph");
    model.result("pg3").feature().create("ptgr16", "PointGraph");
    model.result("pg3").feature().create("ptgr17", "PointGraph");
    model.result("pg3").feature().create("ptgr18", "PointGraph");
    model.result("pg3").feature().create("ptgr19", "PointGraph");
    model.result("pg3").feature().create("ptgr20", "PointGraph");
    model.result("pg3").feature().create("ptgr21", "PointGraph");
    model.result("pg3").feature().create("ptgr22", "PointGraph");
    model.result("pg3").feature().create("ptgr23", "PointGraph");
    model.result("pg3").feature().create("ptgr24", "PointGraph");
    model.result("pg3").feature().create("ptgr25", "PointGraph");
    model.result("pg3").feature().create("ptgr27", "PointGraph");
    model.result("pg3").feature().create("ptgr26", "PointGraph");
    model.result("pg3").feature("ptgr1").selection()
         .set(new int[]{5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 19, 20, 21, 22, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68});
    model.result("pg3").feature("ptgr2").selection().set(new int[]{7});
    model.result("pg3").feature("ptgr3").selection().set(new int[]{63});
    model.result("pg3").feature("ptgr4").selection().set(new int[]{67});
    model.result("pg3").feature("ptgr5").selection().set(new int[]{65});
    model.result("pg3").feature("ptgr6").selection().set(new int[]{9});
    model.result("pg3").feature("ptgr7").selection().set(new int[]{5});
    model.result("pg3").feature("ptgr8").selection().set(new int[]{19});
    model.result("pg3").feature("ptgr9").selection().set(new int[]{61});
    model.result("pg3").feature("ptgr10").selection().set(new int[]{59});
    model.result("pg3").feature("ptgr11").selection().set(new int[]{13});
    model.result("pg3").feature("ptgr12").selection().set(new int[]{11});
    model.result("pg3").feature("ptgr13").selection().set(new int[]{21});
    model.result("pg3").feature("ptgr14").selection().set(new int[]{8});
    model.result("pg3").feature("ptgr15").selection().set(new int[]{64});
    model.result("pg3").feature("ptgr16").selection().set(new int[]{68});
    model.result("pg3").feature("ptgr17").selection().set(new int[]{66});
    model.result("pg3").feature("ptgr18").selection().set(new int[]{10});
    model.result("pg3").feature("ptgr19").selection().set(new int[]{6});
    model.result("pg3").feature("ptgr20").selection().set(new int[]{20});
    model.result("pg3").feature("ptgr21").selection().set(new int[]{62});
    model.result("pg3").feature("ptgr22").selection().set(new int[]{60});
    model.result("pg3").feature("ptgr23").selection().set(new int[]{14});
    model.result("pg3").feature("ptgr24").selection().set(new int[]{12});
    model.result("pg3").feature("ptgr25").selection().set(new int[]{22});
    model.result("pg4").feature().create("ptgr1", "PointGraph");
    model.result("pg4").feature().create("ptgr2", "PointGraph");
    model.result("pg4").feature().create("ptgr3", "PointGraph");
    model.result("pg4").feature().create("ptgr4", "PointGraph");
    model.result("pg4").feature().create("ptgr5", "PointGraph");
    model.result("pg4").feature().create("ptgr6", "PointGraph");
    model.result("pg4").feature().create("ptgr7", "PointGraph");
    model.result("pg4").feature().create("ptgr8", "PointGraph");
    model.result("pg4").feature().create("ptgr9", "PointGraph");
    model.result("pg4").feature().create("ptgr10", "PointGraph");
    model.result("pg4").feature().create("ptgr11", "PointGraph");
    model.result("pg4").feature().create("ptgr12", "PointGraph");
    model.result("pg4").feature().create("ptgr13", "PointGraph");
    model.result("pg4").feature().create("ptgr14", "PointGraph");
    model.result("pg4").feature().create("ptgr15", "PointGraph");
    model.result("pg4").feature().create("ptgr16", "PointGraph");
    model.result("pg4").feature().create("ptgr17", "PointGraph");
    model.result("pg4").feature().create("ptgr18", "PointGraph");
    model.result("pg4").feature().create("ptgr19", "PointGraph");
    model.result("pg4").feature().create("ptgr20", "PointGraph");
    model.result("pg4").feature().create("ptgr21", "PointGraph");
    model.result("pg4").feature().create("ptgr22", "PointGraph");
    model.result("pg4").feature().create("ptgr23", "PointGraph");
    model.result("pg4").feature().create("ptgr24", "PointGraph");
    model.result("pg4").feature().create("ptgr25", "PointGraph");
    model.result("pg4").feature().create("ptgr27", "PointGraph");
    model.result("pg4").feature().create("ptgr26", "PointGraph");
    model.result("pg4").feature("ptgr1").selection()
         .set(new int[]{5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 19, 20, 21, 22, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68});
    model.result("pg4").feature("ptgr2").selection().set(new int[]{7});
    model.result("pg4").feature("ptgr3").selection().set(new int[]{63});
    model.result("pg4").feature("ptgr4").selection().set(new int[]{67});
    model.result("pg4").feature("ptgr5").selection().set(new int[]{65});
    model.result("pg4").feature("ptgr6").selection().set(new int[]{9});
    model.result("pg4").feature("ptgr7").selection().set(new int[]{5});
    model.result("pg4").feature("ptgr8").selection().set(new int[]{19});
    model.result("pg4").feature("ptgr9").selection().set(new int[]{61});
    model.result("pg4").feature("ptgr10").selection().set(new int[]{59});
    model.result("pg4").feature("ptgr11").selection().set(new int[]{13});
    model.result("pg4").feature("ptgr12").selection().set(new int[]{11});
    model.result("pg4").feature("ptgr13").selection().set(new int[]{21});
    model.result("pg4").feature("ptgr14").selection().set(new int[]{8});
    model.result("pg4").feature("ptgr15").selection().set(new int[]{64});
    model.result("pg4").feature("ptgr16").selection().set(new int[]{68});
    model.result("pg4").feature("ptgr17").selection().set(new int[]{66});
    model.result("pg4").feature("ptgr18").selection().set(new int[]{10});
    model.result("pg4").feature("ptgr19").selection().set(new int[]{6});
    model.result("pg4").feature("ptgr20").selection().set(new int[]{20});
    model.result("pg4").feature("ptgr21").selection().set(new int[]{62});
    model.result("pg4").feature("ptgr22").selection().set(new int[]{60});
    model.result("pg4").feature("ptgr23").selection().set(new int[]{14});
    model.result("pg4").feature("ptgr24").selection().set(new int[]{12});
    model.result("pg4").feature("ptgr25").selection().set(new int[]{22});
    model.result().export().create("plot1", "Plot");
    model.result().export().create("plot2", "Plot");

    model.study("std1").feature("param")
         .set("pname", new String[]{"SourceTheta"});
    model.study("std1").feature("param")
         .set("plistarr", new String[]{"range(180 [deg],15 [deg],345 [deg])"});
    model.study("std1").feature("time").set("tunit", "\u00b5s");
    model.study("std1").feature("time").set("tlist", "range(0,10,300)");
    model.study("std1").feature("time").set("rtolactive", true);
    model.study("std1").feature("time").set("rtol", "1e-6");

    model.sol("sol1").attach("std1");
    model.sol("sol1").feature("st1")
         .name("Compile Equations: Time Dependent");
    model.sol("sol1").feature("st1").set("studystep", "time");
    model.sol("sol1").feature("v1").set("control", "time");
    model.sol("sol1").feature("t1").set("tlist", "range(0,10,300)");
    model.sol("sol1").feature("t1").set("timemethod", "genalpha");
    model.sol("sol1").feature("t1").set("maxstepgenalpha", "6");
    model.sol("sol1").feature("t1").set("maxstepgenalphaactive", true);
    model.sol("sol1").feature("t1").set("control", "time");
    model.sol("sol1").feature("t1").set("atolglobal", "1e-6");
    model.sol("sol1").feature("t1").set("rtol", "1e-6");
    model.sol("sol1").feature("t1").set("rhoinf", "0");
    model.sol("sol1").feature("t1").set("tunit", "\u00b5s");
    model.sol("sol2").name("Parametric 2");
    model.sol("sol26").name("Parametric 26");
    model.sol("sol50").name("Parametric 50");
    model.sol("sol53").name("Parametric 53");

    model.batch("p1").set("control", "param");
    model.batch("p1").set("err", true);
    model.batch("p1")
         .set("plistarr", new String[]{"range(180 [deg],15 [deg],345 [deg])"});
    model.batch("p1").set("pname", new String[]{"SourceTheta"});
    model.batch("p1").set("control", "param");
    model.batch("p1").feature("so1").set("psol", "sol53");
    model.batch("p1").feature("so1")
         .set("param", new String[]{"\"SourceTheta\",\"3.141593\"", "\"SourceTheta\",\"3.403392\"", "\"SourceTheta\",\"3.665191\"", "\"SourceTheta\",\"3.926991\"", "\"SourceTheta\",\"4.18879\"", "\"SourceTheta\",\"4.45059\"", "\"SourceTheta\",\"4.712389\"", "\"SourceTheta\",\"4.974188\"", "\"SourceTheta\",\"5.235988\"", "\"SourceTheta\",\"5.497787\"", 
         "\"SourceTheta\",\"5.759587\"", "\"SourceTheta\",\"6.021386\""});
    model.batch("p1").feature("so1").set("seq", "sol1");
    model.batch("p1").attach("std1");
    model.batch("p1").run();

    model.result().dataset("dset5").name("Single Solution");
    model.result().dataset("dset6").name("Parametric Sweep of SourceAngle");
    model.result().dataset("dset7")
         .name("Parametric Sweep of SourceAngle (180-345)");
    model.result("pg3").name("Signal Plots (Single)");
    model.result("pg3").set("ylabel", "-solid.pm (kPa)");
    model.result("pg3").set("xlabel", "Time (ms)");
    model.result("pg3").set("xlabelactive", false);
    model.result("pg3").set("ylabelactive", false);
    model.result("pg3").feature("ptgr1").name("All Signals");
    model.result("pg3").feature("ptgr1").set("expr", "-solid.pm");
    model.result("pg3").feature("ptgr1").set("unit", "kPa");
    model.result("pg3").feature("ptgr1").set("descr", "-solid.pm");
    model.result("pg3").feature("ptgr2").active(false);
    model.result("pg3").feature("ptgr2").name("UB1");
    model.result("pg3").feature("ptgr2").set("expr", "-solid.pm");
    model.result("pg3").feature("ptgr2").set("unit", "kPa");
    model.result("pg3").feature("ptgr2").set("descr", "-solid.pm");
    model.result("pg3").feature("ptgr3").active(false);
    model.result("pg3").feature("ptgr3").name("UB2");
    model.result("pg3").feature("ptgr3").set("expr", "-solid.pm");
    model.result("pg3").feature("ptgr3").set("unit", "kPa");
    model.result("pg3").feature("ptgr3").set("descr", "-solid.pm");
    model.result("pg3").feature("ptgr4").active(false);
    model.result("pg3").feature("ptgr4").name("UB3");
    model.result("pg3").feature("ptgr4").set("expr", "-solid.pm");
    model.result("pg3").feature("ptgr4").set("unit", "kPa");
    model.result("pg3").feature("ptgr4").set("descr", "-solid.pm");
    model.result("pg3").feature("ptgr5").active(false);
    model.result("pg3").feature("ptgr5").name("UB4");
    model.result("pg3").feature("ptgr5").set("expr", "-solid.pm");
    model.result("pg3").feature("ptgr5").set("unit", "kPa");
    model.result("pg3").feature("ptgr5").set("descr", "-solid.pm");
    model.result("pg3").feature("ptgr6").active(false);
    model.result("pg3").feature("ptgr6").name("UB5");
    model.result("pg3").feature("ptgr6").set("expr", "-solid.pm");
    model.result("pg3").feature("ptgr6").set("unit", "kPa");
    model.result("pg3").feature("ptgr6").set("descr", "-solid.pm");
    model.result("pg3").feature("ptgr7").active(false);
    model.result("pg3").feature("ptgr7").name("UB6");
    model.result("pg3").feature("ptgr7").set("expr", "-solid.pm");
    model.result("pg3").feature("ptgr7").set("unit", "kPa");
    model.result("pg3").feature("ptgr7").set("descr", "-solid.pm");
    model.result("pg3").feature("ptgr8").active(false);
    model.result("pg3").feature("ptgr8").name("UW1");
    model.result("pg3").feature("ptgr8").set("expr", "-solid.pm*1e-1");
    model.result("pg3").feature("ptgr8").set("unit", "kPa");
    model.result("pg3").feature("ptgr8").set("descr", "-solid.pm*1e-1");
    model.result("pg3").feature("ptgr9").active(false);
    model.result("pg3").feature("ptgr9").name("UW2");
    model.result("pg3").feature("ptgr9").set("expr", "-solid.pm*1e-1");
    model.result("pg3").feature("ptgr9").set("unit", "kPa");
    model.result("pg3").feature("ptgr9").set("descr", "-solid.pm*1e-1");
    model.result("pg3").feature("ptgr10").active(false);
    model.result("pg3").feature("ptgr10").name("UW3");
    model.result("pg3").feature("ptgr10").set("expr", "-solid.pm*1e-1");
    model.result("pg3").feature("ptgr10").set("unit", "kPa");
    model.result("pg3").feature("ptgr10").set("descr", "-solid.pm*1e-1");
    model.result("pg3").feature("ptgr11").active(false);
    model.result("pg3").feature("ptgr11").name("UW4");
    model.result("pg3").feature("ptgr11").set("expr", "-solid.pm*1e-1");
    model.result("pg3").feature("ptgr11").set("unit", "kPa");
    model.result("pg3").feature("ptgr11").set("descr", "-solid.pm*1e-1");
    model.result("pg3").feature("ptgr12").active(false);
    model.result("pg3").feature("ptgr12").name("UW5");
    model.result("pg3").feature("ptgr12").set("expr", "-solid.pm*1e-1");
    model.result("pg3").feature("ptgr12").set("unit", "kPa");
    model.result("pg3").feature("ptgr12").set("descr", "-solid.pm*1e-1");
    model.result("pg3").feature("ptgr13").active(false);
    model.result("pg3").feature("ptgr13").name("UW6");
    model.result("pg3").feature("ptgr13").set("expr", "-solid.pm*1e-3");
    model.result("pg3").feature("ptgr13").set("unit", "kPa");
    model.result("pg3").feature("ptgr13").set("descr", "-solid.pm*1e-3");
    model.result("pg3").feature("ptgr14").active(false);
    model.result("pg3").feature("ptgr14").name("DB1");
    model.result("pg3").feature("ptgr14").set("expr", "-solid.pm");
    model.result("pg3").feature("ptgr14").set("unit", "kPa");
    model.result("pg3").feature("ptgr14").set("descr", "-solid.pm");
    model.result("pg3").feature("ptgr15").active(false);
    model.result("pg3").feature("ptgr15").name("DB2");
    model.result("pg3").feature("ptgr15").set("expr", "-solid.pm");
    model.result("pg3").feature("ptgr15").set("unit", "kPa");
    model.result("pg3").feature("ptgr15").set("descr", "-solid.pm");
    model.result("pg3").feature("ptgr16").active(false);
    model.result("pg3").feature("ptgr16").name("DB3");
    model.result("pg3").feature("ptgr16").set("expr", "-solid.pm");
    model.result("pg3").feature("ptgr16").set("unit", "kPa");
    model.result("pg3").feature("ptgr16").set("descr", "-solid.pm");
    model.result("pg3").feature("ptgr17").active(false);
    model.result("pg3").feature("ptgr17").name("DB4");
    model.result("pg3").feature("ptgr17").set("expr", "-solid.pm");
    model.result("pg3").feature("ptgr17").set("unit", "kPa");
    model.result("pg3").feature("ptgr17").set("descr", "-solid.pm");
    model.result("pg3").feature("ptgr18").active(false);
    model.result("pg3").feature("ptgr18").name("DB5");
    model.result("pg3").feature("ptgr18").set("expr", "-solid.pm");
    model.result("pg3").feature("ptgr18").set("unit", "kPa");
    model.result("pg3").feature("ptgr18").set("descr", "-solid.pm");
    model.result("pg3").feature("ptgr19").active(false);
    model.result("pg3").feature("ptgr19").name("DB6");
    model.result("pg3").feature("ptgr19").set("expr", "-solid.pm");
    model.result("pg3").feature("ptgr19").set("unit", "kPa");
    model.result("pg3").feature("ptgr19").set("descr", "-solid.pm");
    model.result("pg3").feature("ptgr20").active(false);
    model.result("pg3").feature("ptgr20").name("DW1");
    model.result("pg3").feature("ptgr20").set("expr", "-solid.pm*1e-1");
    model.result("pg3").feature("ptgr20").set("unit", "kPa");
    model.result("pg3").feature("ptgr20").set("descr", "-solid.pm*1e-1");
    model.result("pg3").feature("ptgr21").active(false);
    model.result("pg3").feature("ptgr21").name("DW2");
    model.result("pg3").feature("ptgr21").set("expr", "-solid.pm*1e-1");
    model.result("pg3").feature("ptgr21").set("unit", "kPa");
    model.result("pg3").feature("ptgr21").set("descr", "-solid.pm*1e-1");
    model.result("pg3").feature("ptgr22").active(false);
    model.result("pg3").feature("ptgr22").name("DW3");
    model.result("pg3").feature("ptgr22").set("expr", "-solid.pm*1e-1");
    model.result("pg3").feature("ptgr22").set("unit", "kPa");
    model.result("pg3").feature("ptgr22").set("descr", "-solid.pm*1e-1");
    model.result("pg3").feature("ptgr23").active(false);
    model.result("pg3").feature("ptgr23").name("DW4");
    model.result("pg3").feature("ptgr23").set("expr", "-solid.pm*1e-1");
    model.result("pg3").feature("ptgr23").set("unit", "kPa");
    model.result("pg3").feature("ptgr23").set("descr", "-solid.pm*1e-1");
    model.result("pg3").feature("ptgr24").active(false);
    model.result("pg3").feature("ptgr24").name("DW5");
    model.result("pg3").feature("ptgr24").set("expr", "-solid.pm*1e-1");
    model.result("pg3").feature("ptgr24").set("unit", "kPa");
    model.result("pg3").feature("ptgr24").set("descr", "-solid.pm*1e-1");
    model.result("pg3").feature("ptgr25").active(false);
    model.result("pg3").feature("ptgr25").name("DW6");
    model.result("pg3").feature("ptgr25").set("expr", "-solid.pm*1e-3");
    model.result("pg3").feature("ptgr25").set("unit", "kPa");
    model.result("pg3").feature("ptgr25").set("descr", "-solid.pm*1e-3");
    model.result("pg3").feature("ptgr27").active(false);
    model.result("pg3").feature("ptgr27").name("Upstream");
    model.result("pg3").feature("ptgr27").set("expr", "actd.p_t");
    model.result("pg3").feature("ptgr27").set("unit", "kPa");
    model.result("pg3").feature("ptgr27")
         .set("descr", "Total acoustic pressure field");
    model.result("pg3").feature("ptgr26").active(false);
    model.result("pg3").feature("ptgr26").name("Downstream");
    model.result("pg3").feature("ptgr26").set("expr", "actd.p_t");
    model.result("pg3").feature("ptgr26").set("unit", "kPa");
    model.result("pg3").feature("ptgr26")
         .set("descr", "Total acoustic pressure field");
    model.result("pg4").name("Signal Plots (Sweep)");
    model.result("pg4").set("data", "dset6");
    model.result("pg4").set("ylabel", "-solid.pm (kPa)");
    model.result("pg4").set("xlabel", "Time (ms)");
    model.result("pg4").set("xlabelactive", false);
    model.result("pg4").set("ylabelactive", false);
    model.result("pg4").feature("ptgr1").name("All Signals");
    model.result("pg4").feature("ptgr1").set("expr", "-solid.pm");
    model.result("pg4").feature("ptgr1").set("unit", "kPa");
    model.result("pg4").feature("ptgr1").set("descr", "-solid.pm");
    model.result("pg4").feature("ptgr2").active(false);
    model.result("pg4").feature("ptgr2").name("UB1");
    model.result("pg4").feature("ptgr2").set("expr", "-solid.pm");
    model.result("pg4").feature("ptgr2").set("unit", "kPa");
    model.result("pg4").feature("ptgr2").set("descr", "-solid.pm");
    model.result("pg4").feature("ptgr3").active(false);
    model.result("pg4").feature("ptgr3").name("UB2");
    model.result("pg4").feature("ptgr3").set("expr", "-solid.pm");
    model.result("pg4").feature("ptgr3").set("unit", "kPa");
    model.result("pg4").feature("ptgr3").set("descr", "-solid.pm");
    model.result("pg4").feature("ptgr4").active(false);
    model.result("pg4").feature("ptgr4").name("UB3");
    model.result("pg4").feature("ptgr4").set("expr", "-solid.pm");
    model.result("pg4").feature("ptgr4").set("unit", "kPa");
    model.result("pg4").feature("ptgr4").set("descr", "-solid.pm");
    model.result("pg4").feature("ptgr5").active(false);
    model.result("pg4").feature("ptgr5").name("UB4");
    model.result("pg4").feature("ptgr5").set("expr", "-solid.pm");
    model.result("pg4").feature("ptgr5").set("unit", "kPa");
    model.result("pg4").feature("ptgr5").set("descr", "-solid.pm");
    model.result("pg4").feature("ptgr6").active(false);
    model.result("pg4").feature("ptgr6").name("UB5");
    model.result("pg4").feature("ptgr6").set("expr", "-solid.pm");
    model.result("pg4").feature("ptgr6").set("unit", "kPa");
    model.result("pg4").feature("ptgr6").set("descr", "-solid.pm");
    model.result("pg4").feature("ptgr7").active(false);
    model.result("pg4").feature("ptgr7").name("UB6");
    model.result("pg4").feature("ptgr7").set("expr", "-solid.pm");
    model.result("pg4").feature("ptgr7").set("unit", "kPa");
    model.result("pg4").feature("ptgr7").set("descr", "-solid.pm");
    model.result("pg4").feature("ptgr8").active(false);
    model.result("pg4").feature("ptgr8").name("UW1");
    model.result("pg4").feature("ptgr8").set("expr", "-solid.pm*1e-1");
    model.result("pg4").feature("ptgr8").set("unit", "kPa");
    model.result("pg4").feature("ptgr8").set("descr", "-solid.pm*1e-1");
    model.result("pg4").feature("ptgr9").active(false);
    model.result("pg4").feature("ptgr9").name("UW2");
    model.result("pg4").feature("ptgr9").set("expr", "-solid.pm*1e-1");
    model.result("pg4").feature("ptgr9").set("unit", "kPa");
    model.result("pg4").feature("ptgr9").set("descr", "-solid.pm*1e-1");
    model.result("pg4").feature("ptgr10").active(false);
    model.result("pg4").feature("ptgr10").name("UW3");
    model.result("pg4").feature("ptgr10").set("expr", "-solid.pm*1e-1");
    model.result("pg4").feature("ptgr10").set("unit", "kPa");
    model.result("pg4").feature("ptgr10").set("descr", "-solid.pm*1e-1");
    model.result("pg4").feature("ptgr11").active(false);
    model.result("pg4").feature("ptgr11").name("UW4");
    model.result("pg4").feature("ptgr11").set("expr", "-solid.pm*1e-1");
    model.result("pg4").feature("ptgr11").set("unit", "kPa");
    model.result("pg4").feature("ptgr11").set("descr", "-solid.pm*1e-1");
    model.result("pg4").feature("ptgr12").active(false);
    model.result("pg4").feature("ptgr12").name("UW5");
    model.result("pg4").feature("ptgr12").set("expr", "-solid.pm*1e-1");
    model.result("pg4").feature("ptgr12").set("unit", "kPa");
    model.result("pg4").feature("ptgr12").set("descr", "-solid.pm*1e-1");
    model.result("pg4").feature("ptgr13").active(false);
    model.result("pg4").feature("ptgr13").name("UW6");
    model.result("pg4").feature("ptgr13").set("expr", "-solid.pm*1e-3");
    model.result("pg4").feature("ptgr13").set("unit", "kPa");
    model.result("pg4").feature("ptgr13").set("descr", "-solid.pm*1e-3");
    model.result("pg4").feature("ptgr14").active(false);
    model.result("pg4").feature("ptgr14").name("DB1");
    model.result("pg4").feature("ptgr14").set("expr", "-solid.pm");
    model.result("pg4").feature("ptgr14").set("unit", "kPa");
    model.result("pg4").feature("ptgr14").set("descr", "-solid.pm");
    model.result("pg4").feature("ptgr15").active(false);
    model.result("pg4").feature("ptgr15").name("DB2");
    model.result("pg4").feature("ptgr15").set("expr", "-solid.pm");
    model.result("pg4").feature("ptgr15").set("unit", "kPa");
    model.result("pg4").feature("ptgr15").set("descr", "-solid.pm");
    model.result("pg4").feature("ptgr16").active(false);
    model.result("pg4").feature("ptgr16").name("DB3");
    model.result("pg4").feature("ptgr16").set("expr", "-solid.pm");
    model.result("pg4").feature("ptgr16").set("unit", "kPa");
    model.result("pg4").feature("ptgr16").set("descr", "-solid.pm");
    model.result("pg4").feature("ptgr17").active(false);
    model.result("pg4").feature("ptgr17").name("DB4");
    model.result("pg4").feature("ptgr17").set("expr", "-solid.pm");
    model.result("pg4").feature("ptgr17").set("unit", "kPa");
    model.result("pg4").feature("ptgr17").set("descr", "-solid.pm");
    model.result("pg4").feature("ptgr18").active(false);
    model.result("pg4").feature("ptgr18").name("DB5");
    model.result("pg4").feature("ptgr18").set("expr", "-solid.pm");
    model.result("pg4").feature("ptgr18").set("unit", "kPa");
    model.result("pg4").feature("ptgr18").set("descr", "-solid.pm");
    model.result("pg4").feature("ptgr19").active(false);
    model.result("pg4").feature("ptgr19").name("DB6");
    model.result("pg4").feature("ptgr19").set("expr", "-solid.pm");
    model.result("pg4").feature("ptgr19").set("unit", "kPa");
    model.result("pg4").feature("ptgr19").set("descr", "-solid.pm");
    model.result("pg4").feature("ptgr20").active(false);
    model.result("pg4").feature("ptgr20").name("DW1");
    model.result("pg4").feature("ptgr20").set("expr", "-solid.pm*1e-1");
    model.result("pg4").feature("ptgr20").set("unit", "kPa");
    model.result("pg4").feature("ptgr20").set("descr", "-solid.pm*1e-1");
    model.result("pg4").feature("ptgr21").active(false);
    model.result("pg4").feature("ptgr21").name("DW2");
    model.result("pg4").feature("ptgr21").set("expr", "-solid.pm*1e-1");
    model.result("pg4").feature("ptgr21").set("unit", "kPa");
    model.result("pg4").feature("ptgr21").set("descr", "-solid.pm*1e-1");
    model.result("pg4").feature("ptgr22").active(false);
    model.result("pg4").feature("ptgr22").name("DW3");
    model.result("pg4").feature("ptgr22").set("expr", "-solid.pm*1e-1");
    model.result("pg4").feature("ptgr22").set("unit", "kPa");
    model.result("pg4").feature("ptgr22").set("descr", "-solid.pm*1e-1");
    model.result("pg4").feature("ptgr23").active(false);
    model.result("pg4").feature("ptgr23").name("DW4");
    model.result("pg4").feature("ptgr23").set("expr", "-solid.pm*1e-1");
    model.result("pg4").feature("ptgr23").set("unit", "kPa");
    model.result("pg4").feature("ptgr23").set("descr", "-solid.pm*1e-1");
    model.result("pg4").feature("ptgr24").active(false);
    model.result("pg4").feature("ptgr24").name("DW5");
    model.result("pg4").feature("ptgr24").set("expr", "-solid.pm*1e-1");
    model.result("pg4").feature("ptgr24").set("unit", "kPa");
    model.result("pg4").feature("ptgr24").set("descr", "-solid.pm*1e-1");
    model.result("pg4").feature("ptgr25").active(false);
    model.result("pg4").feature("ptgr25").name("DW6");
    model.result("pg4").feature("ptgr25").set("expr", "-solid.pm*1e-3");
    model.result("pg4").feature("ptgr25").set("unit", "kPa");
    model.result("pg4").feature("ptgr25").set("descr", "-solid.pm*1e-3");
    model.result("pg4").feature("ptgr27").active(false);
    model.result("pg4").feature("ptgr27").name("Upstream");
    model.result("pg4").feature("ptgr27").set("expr", "actd.p_t");
    model.result("pg4").feature("ptgr27").set("unit", "kPa");
    model.result("pg4").feature("ptgr27")
         .set("descr", "Total acoustic pressure field");
    model.result("pg4").feature("ptgr26").active(false);
    model.result("pg4").feature("ptgr26").name("Downstream");
    model.result("pg4").feature("ptgr26").set("expr", "actd.p_t");
    model.result("pg4").feature("ptgr26").set("unit", "kPa");
    model.result("pg4").feature("ptgr26")
         .set("descr", "Total acoustic pressure field");
    model.result().export("plot1").name("All Signals (Single)");
    model.result().export("plot1")
         .set("filename", "C:\\Users\\plane\\Desktop\\COMSOL\\Data\\SCM\\AllSignals_bipolar_0cm_0cm_300us.csv");
    model.result().export("plot1").set("sort", true);
    model.result().export("plot2").name("All Signals (Sweep)");
    model.result().export("plot2")
         .set("filename", "C:\\Users\\plane\\Desktop\\COMSOL\\Data\\SCM\\AllSignals_bipolar_5cm_165deg_300us.csv");
    model.result().export("plot2").set("plotgroup", "pg4");
    model.result().export("plot2").set("sort", true);

    return model;
  }

}
