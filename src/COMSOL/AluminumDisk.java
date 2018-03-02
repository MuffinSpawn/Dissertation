/*
 * AluminumDisk.java
 */

import com.comsol.model.*;
import com.comsol.model.util.*;

/** Model exported on Jan 15 2016, 16:15 by COMSOL 4.4.0.195. */
public class AluminumDisk {

  public static void main(String[] args) {
    run();
  }

  public static Model run() {
    Model model = ModelUtil.create("Model");

    model.modelPath("C:\\Users\\plane\\Desktop\\COMSOL");

    model.name("Aluminum Disk 24cm Separation (Save).mph");

    model.param()
         .set("diskRadius", "15.24 [cm]", "RF cavity outer diameter (15.24[cm])");
    model.param().set("diskHeight", "5.08 [cm]", "End cap height (5.08[cm])");
    model.param().set("sourceRadius", "2 [mm]");
    model.param()
         .set("sourceRho", "12.0 [cm]", "Distance to source from z-axis");
    model.param().set("sourceAngle", "0 [deg]", "Source angle");
    model.param()
         .set("sourceX", "sourceRho * cos(sourceAngle)", "Source x position");
    model.param()
         .set("sourceY", "sourceRho * sin(sourceAngle)", "Source y position");
    model.param().set("sourceZ", "diskHeight", "Source z position");
    model.param().set("t0", "1 [ms]", "Spark duration");
    model.param().set("sourceH", "1.0 [mm]");
    model.param().set("gamma", "3", "Spark decay rate");
    model.param().set("lambda", "1", "Cycles per t0");
    model.param().set("F0", "1e3 [N]", "Spark force magnitude");
    model.param()
         .set("f0", "2.17e4 [N/m^2]", "Spark force density magnitude");
    model.param().set("t1", "0 [us]", "Upstream spark delay");
    model.param().set("t2", "t0 + t1", "Upstream spark end time");
    model.param().set("micRadius", "1.27 [cm]");
    model.param().set("micHeight", "1.0 [cm]");
    model.param().set("micRho", "12.0 [cm]");
    model.param().set("micAngle", "180 [deg]");
    model.param().set("micX", "micRho * cos(micAngle)");
    model.param().set("micY", "micRho * sin(micAngle)");
    model.param().set("micZ", "diskHeight");
    model.param().set("alpha_dM", "0");
    model.param().set("beta_dK", "1e-6", "Stiffness damping factor");

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
    model.geom("geom1").feature().create("cyl3", "Cylinder");
    model.geom("geom1").feature().create("pt2", "Point");
    model.geom("geom1").feature().create("pt3", "Point");
    model.geom("geom1").feature("cyl1").name("Disk");
    model.geom("geom1").feature("cyl1").set("r", "diskRadius");
    model.geom("geom1").feature("cyl1")
         .set("pos", new String[]{"0", "0", "0"});
    model.geom("geom1").feature("cyl1").set("h", "diskHeight");
    model.geom("geom1").feature("cyl3").name("Source");
    model.geom("geom1").feature("cyl3").set("r", "sourceRadius");
    model.geom("geom1").feature("cyl3")
         .set("pos", new String[]{"sourceX", "sourceY", "sourceZ"});
    model.geom("geom1").feature("cyl3").set("h", "sourceRadius");
    model.geom("geom1").feature("pt2").name("Impact Point");
    model.geom("geom1").feature("pt2").setIndex("p", "sourceX", 0, 0);
    model.geom("geom1").feature("pt2").setIndex("p", "sourceY", 1, 0);
    model.geom("geom1").feature("pt2").setIndex("p", "sourceZ", 2, 0);
    model.geom("geom1").feature("pt3").setIndex("p", "micX", 0, 0);
    model.geom("geom1").feature("pt3").setIndex("p", "micY", 1, 0);
    model.geom("geom1").feature("pt3").setIndex("p", "micZ", 2, 0);
    model.geom("geom1").run();

    model.view().create("view2", 2);

    model.material().create("mat1");
    model.material("mat1").propertyGroup()
         .create("Enu", "Young's modulus and Poisson's ratio");
    model.material("mat1").propertyGroup().create("Murnaghan", "Murnaghan");
    model.material("mat1").propertyGroup()
         .create("Lame", "Lam\u00e9 parameters");

    model.physics().create("solid", "SolidMechanics", "geom1");
    model.physics("solid").feature("lemm1").feature()
         .create("dmp1", "Damping", 3);
    model.physics("solid").feature().create("bl1", "BodyLoad", 3);
    model.physics("solid").feature("bl1").selection().set(new int[]{2});

    model.mesh().create("mesh1", "geom1");
    model.mesh("mesh1").feature().create("ftet1", "FreeTet");
    model.mesh("mesh1").feature("ftet1").feature().create("size4", "Size");
    model.mesh("mesh1").feature("ftet1").feature("size4").selection()
         .geom("geom1", 3);
    model.mesh("mesh1").feature("ftet1").feature("size4").selection()
         .set(new int[]{2});

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

    model.physics("solid").feature("lemm1").feature("dmp1")
         .set("alpha_dM", "alpha_dM");
    model.physics("solid").feature("lemm1").feature("dmp1")
         .set("beta_dK", "beta_dK");
    model.physics("solid").feature("bl1")
         .set("Ftot", new String[][]{{"0"}, {"0"}, {"-Impact(t)"}});
    model.physics("solid").feature("bl1").set("LoadType", "TotalForce");
    model.physics("solid").feature("bl1").name("Impact Source Impulse");

    model.mesh("mesh1").feature("ftet1").feature("size4").name("Source");
    model.mesh("mesh1").feature("ftet1").feature("size4").set("hauto", 3);
    model.mesh("mesh1").run();

    model.result().table("evl3").name("Evaluation 3D");
    model.result().table("evl3").comments("Interactive 3D values");

    model.study().create("std1");
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

    model.result().dataset().create("pw5_ds1", "Function1D");
    model.result().dataset().create("pw5_ds2", "Function1D");
    model.result().dataset().create("pw5_ds3", "Function1D");
    model.result().dataset().create("pw3_ds1", "Function1D");
    model.result().dataset().create("pw3_ds2", "Function1D");
    model.result().dataset().create("pw3_ds3", "Function1D");
    model.result().dataset().create("pw5_ds4", "Function1D");
    model.result().dataset().create("pw5_ds5", "Function1D");
    model.result().dataset().create("pw5_ds6", "Function1D");
    model.result().create("pg10", "PlotGroup1D");
    model.result().create("pg1", "PlotGroup1D");
    model.result("pg10").feature().create("ptgr1", "PointGraph");
    model.result("pg10").feature().create("ptgr2", "PointGraph");
    model.result("pg10").feature("ptgr1").selection().set(new int[]{12});
    model.result("pg10").feature("ptgr2").selection().set(new int[]{3});
    model.result("pg1").feature().create("ptgr1", "PointGraph");
    model.result().export().create("plot1", "Plot");

    model.study("std1").name("Time-Dependent Study");
    model.study("std1").feature("time").set("tunit", "\u00b5s");
    model.study("std1").feature("time").set("tlist", "range(0,25,25000)");
    model.study("std1").feature("time").set("rtolactive", true);
    model.study("std1").feature("time").set("rtol", "1e-6");

    model.sol("sol1").attach("std1");
    model.sol("sol1").feature("st1")
         .name("Compile Equations: Time Dependent");
    model.sol("sol1").feature("st1").set("studystep", "time");
    model.sol("sol1").feature("v1").set("control", "time");
    model.sol("sol1").feature("v1").feature("mod1_u")
         .set("scalemethod", "manual");
    model.sol("sol1").feature("v1").feature("mod1_u")
         .set("scaleval", "1e-2*0.46813335706826115");
    model.sol("sol1").feature("t1").set("tlist", "range(0,25,25000)");
    model.sol("sol1").feature("t1").set("timemethod", "genalpha");
    model.sol("sol1").feature("t1").set("timestepgenalpha", "25e-6");
    model.sol("sol1").feature("t1").set("tstepsgenalpha", "manual");
    model.sol("sol1").feature("t1").set("control", "time");
    model.sol("sol1").feature("t1").set("atolglobal", "1e-6");
    model.sol("sol1").feature("t1").set("rtol", "1e-6");
    model.sol("sol1").feature("t1").set("rhoinf", ".5");
    model.sol("sol1").feature("t1").set("tunit", "\u00b5s");
    model.sol("sol1").feature("t1").feature("dDef")
         .set("linsolver", "pardiso");

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
    model.result("pg10").name("Pressure Plots");
    model.result("pg10").set("ylabel", "-solid.u_ttZ (m/s<sup>2</sup>)");
    model.result("pg10").set("xlabel", "Time (\u00b5s)");
    model.result("pg10").set("xlabelactive", false);
    model.result("pg10").set("ylabelactive", false);
    model.result("pg10").feature("ptgr1").active(false);
    model.result("pg10").feature("ptgr1").name("Source");
    model.result("pg10").feature("ptgr1").set("expr", "solid.u_ttZ*1e-1");
    model.result("pg10").feature("ptgr1").set("unit", "m/s^2");
    model.result("pg10").feature("ptgr1").set("descr", "solid.u_ttZ*1e-1");
    model.result("pg10").feature("ptgr2").name("Microphone");
    model.result("pg10").feature("ptgr2").set("expr", "-solid.u_ttZ");
    model.result("pg10").feature("ptgr2").set("unit", "m/s^2");
    model.result("pg10").feature("ptgr2").set("descr", "-solid.u_ttZ");
    model.result("pg1").name("Normal Acceleration Plots");
    model.result("pg1").set("preserveaspect", true);
    model.result("pg1")
         .set("ylabel", "Acceleration, Z component (m/s<sup>2</sup>)");
    model.result("pg1").set("xlabel", "Time (s)");
    model.result("pg1").set("xlabelactive", false);
    model.result("pg1").set("ylabelactive", false);
    model.result("pg1").feature("ptgr1").name("Source");
    model.result("pg1").feature("ptgr1").set("expr", "solid.u_ttZ");
    model.result("pg1").feature("ptgr1")
         .set("const", new String[][]{{"mod1.solid.refpntx", "0", "Reference point for moment computation, x coordinate"}, {"mod1.solid.refpnty", "0", "Reference point for moment computation, y coordinate"}, {"mod1.solid.refpntz", "0", "Reference point for moment computation, z coordinate"}});
    model.result("pg1").feature("ptgr1").set("linecolor", "red");
    model.result("pg1").feature("ptgr1").set("unit", "m/s^2");
    model.result("pg1").feature("ptgr1")
         .set("descr", "Acceleration, Z component");
    model.result("pg1").feature("ptgr1").set("markerpos", "datapoints");
    model.result("pg1").feature("ptgr1").set("linemarker", "point");
    model.result("pg1").feature("ptgr1").set("linestyle", "dotted");

    return model;
  }

}
