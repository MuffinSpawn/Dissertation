/*
 * HPCShockwave2D.java
 */

import com.comsol.model.*;
import com.comsol.model.util.*;

/** Model exported on Jan 17 2016, 15:31 by COMSOL 4.4.0.195. */
public class HPCShockwave2D {

  public static void main(String[] args) {
    run();
  }

  public static Model run() {
    Model model = ModelUtil.create("Model");

    model.modelPath("C:\\Users\\plane\\Desktop\\COMSOL");

    model.name("HPRF Shockwave 2D.mph");

    model.param().set("InnerRadius", "11.43 [cm]");
    model.param().set("OuterRadius", "15.24 [cm]");
    model.param().set("InnerHeight", "8.128 [cm]");
    model.param().set("EndHeight", "5.08 [cm]");
    model.param().set("ButtonRadius", "2.54 [cm]");
    model.param().set("ShimHeight", "0.635 [cm]");
    model.param().set("ShimGapRadius", "2.2225 [cm]");
    model.param().set("ShimGapHeight", ".15748 [cm]");
    model.param().set("c_p_SS", "5800 [m/s]", "5790 [m/s]");
    model.param().set("c_s_SS", "3070 [m/s]", "3100 [m/s]");
    model.param().set("c_p_Cu", "4760 [m/s]", "4760 [m/s]");
    model.param().set("c_s_Cu", "2523 [m/s]", "2325 [m/s]");
    model.param().set("c_N2", "540 [m/s]");
    model.param().set("z_S1", "0 [cm]");
    model.param().set("A_r", "1e3 [m^2/s]");
    model.param().set("A_z", "1e3 [N]");
    model.param().set("f0", "1e6[Hz]", "Gaussian Bandwidth");
    model.param().set("t0", "1 [us]", "Gaussian Mean");
    model.param().set("r_S0", "0.919*InnerRadius");
    model.param().set("r_S5", "0.919*InnerRadius");
    model.param().set("rho_N2", "37.5 [kg/m^3]");
    model.param().set("k_tot_bf_r", "0 [N/m]");
    model.param().set("k_tot_bf_z", "1e8 [N/m]");
    model.param().set("k_tot_ff_r", "0 [N/m]");
    model.param().set("k_tot_ff_z", "0 [N/m]");

    model.modelNode().create("comp1");

    model.func().create("gp1", "GaussianPulse");
    model.func("gp1").set("sigma", "5e-7");
    model.func("gp1").set("location", "1e-6");

    model.geom().create("geom1", 2);
    model.geom("geom1").axisymmetric(true);
    model.geom("geom1").feature().create("r4", "Rectangle");
    model.geom("geom1").feature().create("r1", "Rectangle");
    model.geom("geom1").feature().create("r2", "Rectangle");
    model.geom("geom1").feature().create("r5", "Rectangle");
    model.geom("geom1").feature().create("dif1", "Difference");
    model.geom("geom1").feature().create("r3", "Rectangle");
    model.geom("geom1").feature().create("r6", "Rectangle");
    model.geom("geom1").feature().create("dif2", "Difference");
    model.geom("geom1").feature().create("c1", "Circle");
    model.geom("geom1").feature().create("c2", "Circle");
    model.geom("geom1").feature().create("r7", "Rectangle");
    model.geom("geom1").feature().create("r10", "Rectangle");
    model.geom("geom1").feature().create("r11", "Rectangle");
    model.geom("geom1").feature().create("r12", "Rectangle");
    model.geom("geom1").feature().create("r13", "Rectangle");
    model.geom("geom1").feature().create("b1", "BezierPolygon");
    model.geom("geom1").feature().create("r8", "Rectangle");
    model.geom("geom1").feature().create("pt1", "Point");
    model.geom("geom1").feature().create("pt2", "Point");
    model.geom("geom1").feature().create("pt3", "Point");
    model.geom("geom1").feature().create("r14", "Rectangle");
    model.geom("geom1").feature().create("par1", "Partition");
    model.geom("geom1").feature().create("b2", "BezierPolygon");
    model.geom("geom1").feature("r4").name("Cavity");
    model.geom("geom1").feature("r4")
         .set("size", new String[]{"15.24[cm]", "InnerHeight+2*EndHeight"});
    model.geom("geom1").feature("r4")
         .set("pos", new String[]{"0", "-InnerHeight/2-EndHeight"});
    model.geom("geom1").feature("r1").name("Resonance Chamber");
    model.geom("geom1").feature("r1")
         .set("size", new String[]{"InnerRadius", "InnerHeight"});
    model.geom("geom1").feature("r1")
         .set("pos", new String[]{"0", "-InnerHeight/2"});
    model.geom("geom1").feature("r2").name("Upstream Button Shim");
    model.geom("geom1").feature("r2")
         .set("size", new String[]{"ButtonRadius", "ShimHeight"});
    model.geom("geom1").feature("r2")
         .set("pos", new String[]{"0", "-InnerHeight/2"});
    model.geom("geom1").feature("r5").name("Upstream Button Shim Gap");
    model.geom("geom1").feature("r5")
         .set("size", new String[]{"ShimGapRadius", "ShimGapHeight"});
    model.geom("geom1").feature("r5")
         .set("pos", new String[]{"0", "-InnerHeight/2"});
    model.geom("geom1").feature("dif1").selection("input")
         .set(new String[]{"r2"});
    model.geom("geom1").feature("dif1").selection("input2")
         .set(new String[]{"r5"});
    model.geom("geom1").feature("r3").name("Downstream Button Shim");
    model.geom("geom1").feature("r3")
         .set("size", new String[]{"ButtonRadius", "ShimHeight"});
    model.geom("geom1").feature("r3")
         .set("pos", new String[]{"0", "InnerHeight/2-ShimHeight"});
    model.geom("geom1").feature("r6").name("Downstream Button Shim Gap");
    model.geom("geom1").feature("r6")
         .set("size", new String[]{"ShimGapRadius", "ShimGapHeight"});
    model.geom("geom1").feature("r6")
         .set("pos", new String[]{"0", "InnerHeight/2-ShimGapHeight"});
    model.geom("geom1").feature("dif2").selection("input")
         .set(new String[]{"r3"});
    model.geom("geom1").feature("dif2").selection("input2")
         .set(new String[]{"r6"});
    model.geom("geom1").feature("c1").name("Upstream Button");
    model.geom("geom1").feature("c1").set("angle", "90");
    model.geom("geom1").feature("c1").set("r", "ButtonRadius");
    model.geom("geom1").feature("c1")
         .set("pos", new String[]{"0", "-InnerHeight/2+ShimHeight"});
    model.geom("geom1").feature("c2").name("Downstream Button");
    model.geom("geom1").feature("c2").set("angle", "90");
    model.geom("geom1").feature("c2").set("r", "ButtonRadius");
    model.geom("geom1").feature("c2").set("rot", "270");
    model.geom("geom1").feature("c2")
         .set("pos", new String[]{"0", "InnerHeight/2-ShimHeight"});
    model.geom("geom1").feature("r7").name("Beam Hole");
    model.geom("geom1").feature("r7")
         .set("size", new String[]{"0.00508", "3.4925 [cm]"});
    model.geom("geom1").feature("r7")
         .set("pos", new String[]{"0", "-InnerHeight/2-EndHeight"});
    model.geom("geom1").feature("r10").name("Upstream Button Bolt Bore");
    model.geom("geom1").feature("r10")
         .set("size", new String[]{"0.00508", "4[cm]"});
    model.geom("geom1").feature("r10")
         .set("pos", new String[]{"0", "-InnerHeight/2-1.27[cm]"});
    model.geom("geom1").feature("r11").name("Upstream Button Bolt");
    model.geom("geom1").feature("r11")
         .set("size", new String[]{".4445 [cm]", "2.54 [cm]"});
    model.geom("geom1").feature("r11")
         .set("pos", new String[]{".508 [cm]", "-InnerHeight/2-1.27[cm]"});
    model.geom("geom1").feature("r12")
         .name("Upstream Button Bolt Drill Space");
    model.geom("geom1").feature("r12")
         .set("size", new String[]{".23335 [cm]", ".23335 [cm]"});
    model.geom("geom1").feature("r12")
         .set("pos", new String[]{".508 [cm]", "-InnerHeight/2+1.27 [cm]"});
    model.geom("geom1").feature("r13").name("Downstream Button Bolt");
    model.geom("geom1").feature("r13")
         .set("size", new String[]{".9525 [cm]", "2.54 [cm]"});
    model.geom("geom1").feature("r13")
         .set("pos", new String[]{"0", "InnerHeight/2-1.27[cm]"});
    model.geom("geom1").feature("b1").set("degree", new String[]{"1"});
    model.geom("geom1").feature("b1")
         .set("p", new String[][]{{"0.00508", "0"}, {"-0.01334", "-0.011"}});
    model.geom("geom1").feature("b1").set("w", new String[]{"1", "1"});
    model.geom("geom1").feature("r8").active(false);
    model.geom("geom1").feature("r8").name("Gas Inlet");
    model.geom("geom1").feature("r8")
         .set("size", new String[]{"0.009075", "EndHeight"});
    model.geom("geom1").feature("r8")
         .set("pos", new String[]{"0.0851", "-CavityHeight/2-EndHeight"});
    model.geom("geom1").feature("pt1").name("S1");
    model.geom("geom1").feature("pt1").setIndex("p", "OuterRadius", 0, 0);
    model.geom("geom1").feature("pt1").setIndex("p", "z_S1", 1, 0);
    model.geom("geom1").feature("pt2").name("S0");
    model.geom("geom1").feature("pt2").setIndex("p", "r_S0", 0, 0);
    model.geom("geom1").feature("pt2")
         .setIndex("p", "-InnerHeight/2-EndHeight", 1, 0);
    model.geom("geom1").feature("pt3").name("S5");
    model.geom("geom1").feature("pt3").setIndex("p", "r_S5", 0, 0);
    model.geom("geom1").feature("pt3")
         .setIndex("p", "InnerHeight/2+EndHeight", 1, 0);
    model.geom("geom1").feature("r14")
         .set("size", new String[]{"OuterRadius-InnerRadius", "InnerHeight"});
    model.geom("geom1").feature("r14")
         .set("pos", new String[]{"InnerRadius", "-InnerHeight/2"});
    model.geom("geom1").feature("par1").active(false);
    model.geom("geom1").feature("par1").selection("input")
         .set(new String[]{"r4"});
    model.geom("geom1").feature("par1").selection("tool")
         .set(new String[]{"r14"});
    model.geom("geom1").feature("b2").set("degree", new String[]{"1"});
    model.geom("geom1").feature("b2")
         .set("p", new String[][]{{"0.07", "0.07"}, {"0.04", "-0.04"}});
    model.geom("geom1").feature("b2").set("w", new String[]{"1", "1"});
    model.geom("geom1").run();

    model.view().create("view2", 3);

    model.material().create("mat4");
    model.material("mat4").propertyGroup("def").func()
         .create("eta", "Piecewise");
    model.material("mat4").propertyGroup("def").func()
         .create("Cp", "Piecewise");
    model.material("mat4").propertyGroup("def").func()
         .create("rho", "Analytic");
    model.material("mat4").propertyGroup("def").func()
         .create("k", "Piecewise");
    model.material("mat4").propertyGroup().create("idealGas", "Ideal gas");
    model.material("mat4").selection()
         .set(new int[]{3, 4, 5, 6, 7, 9, 21, 24});
    model.material().create("mat1");
    model.material("mat1").propertyGroup("def").func()
         .create("eta", "Piecewise");
    model.material("mat1").propertyGroup("def").func()
         .create("Cp", "Piecewise");
    model.material("mat1").propertyGroup("def").func()
         .create("rho", "Analytic");
    model.material("mat1").propertyGroup("def").func()
         .create("k", "Piecewise");
    model.material().create("mat2");
    model.material("mat2").propertyGroup()
         .create("Enu", "Young's modulus and Poisson's ratio");
    model.material("mat2").propertyGroup()
         .create("linzRes", "Linearized resistivity");
    model.material("mat2").propertyGroup()
         .create("CpCs", "Pressure-wave and shear-wave speeds");
    model.material("mat2").selection().set(new int[]{8, 10, 22, 23});
    model.material().create("mat3");
    model.material("mat3").propertyGroup()
         .create("comp1", "User-defined property group");
    model.material("mat3").propertyGroup()
         .create("CpCs", "Pressure-wave and shear-wave speeds");
    model.material("mat3").selection()
         .set(new int[]{2, 11, 12, 13, 14, 15, 16, 17, 18, 19, 25});
    model.material().create("mat5");
    model.material("mat5").propertyGroup("def").func()
         .create("eta", "Piecewise");
    model.material("mat5").propertyGroup("def").func()
         .create("Cp", "Piecewise");
    model.material("mat5").propertyGroup("def").func()
         .create("rho", "Analytic");
    model.material("mat5").propertyGroup("def").func()
         .create("k", "Piecewise");
    model.material("mat5").propertyGroup("def").func()
         .create("cs", "Analytic");
    model.material("mat5").propertyGroup().create("idealGas", "Ideal gas");
    model.material("mat5").selection().set(new int[]{1});

    model.physics().create("actd", "TransientPressureAcoustics", "geom1");
    model.physics("actd").selection().set(new int[]{6, 7, 8, 9, 19, 21, 24});
    model.physics("actd").feature()
         .create("aas1", "TransientAcousticAxiSource", 1);
    model.physics().create("astd", "TransientAcousticSolid", "geom1");
    model.physics("astd").selection()
         .set(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 21, 22, 23, 24, 25});
    model.physics("astd").feature("dlemm1").selection()
         .set(new int[]{2, 11, 12, 13, 14, 15, 16, 17, 18, 19, 25});
    model.physics("astd").feature().create("dlemm2", "LinearElasticModel", 2);
    model.physics("astd").feature("dlemm2").selection()
         .set(new int[]{8, 10, 22, 23});
    model.physics("astd").feature().create("fix1", "Fixed", 2);
    model.physics("astd").feature("fix1").selection()
         .set(new int[]{6, 7, 8, 10, 11, 12, 19, 23});
    model.physics("astd").feature().create("init2", "init", 2);
    model.physics("astd").feature("init2").selection()
         .set(new int[]{6, 7, 8, 19});
    model.physics("astd").feature().create("init3", "init", 2);
    model.physics("astd").feature("init3").selection()
         .set(new int[]{10, 11, 12, 23});
    model.physics("astd").feature()
         .create("gpsa1", "GaussianPulseAxiSource", 1);
    model.physics("astd").feature("gpsa1").selection().set(new int[]{17});
    model.physics("astd").feature().create("bndl1", "BoundaryLoad", 1);
    model.physics("astd").feature("bndl1").selection().set(new int[]{78, 79});
    model.physics("astd").feature().create("tel1", "ThinElasticLayer", 1);
    model.physics("astd").feature("tel1").selection().set(new int[]{61, 63});
    model.physics("astd").feature().create("tel2", "ThinElasticLayer", 1);
    model.physics("astd").feature("tel2").selection().set(new int[]{72, 73});

    model.mesh().create("mesh1", "geom1");
    model.mesh("mesh1").feature().create("ftri1", "FreeTri");

    model.view("view1").axis().set("xmin", "-0.1170433908700943");
    model.view("view1").axis().set("ymin", "-0.16206642985343933");
    model.view("view1").axis().set("xmax", "0.2694433927536011");
    model.view("view1").axis().set("ymax", "0.16206642985343933");

    model.material("mat4").name("Nitrogen");
    model.material("mat4").propertyGroup("def").func("eta")
         .set("pieces", new String[][]{{"200.0", "1200.0", "1.77230303E-6+6.27427545E-8*T^1-3.47278555E-11*T^2+1.01243201E-14*T^3"}});
    model.material("mat4").propertyGroup("def").func("eta").set("arg", "T");
    model.material("mat4").propertyGroup("def").func("Cp")
         .set("pieces", new String[][]{{"200.0", "1200.0", "1088.22121-0.365941919*T^1+7.88715035E-4*T^2-3.749223E-7*T^3+3.17599068E-11*T^4"}});
    model.material("mat4").propertyGroup("def").func("Cp").set("arg", "T");
    model.material("mat4").propertyGroup("def").func("rho")
         .set("args", new String[]{"pA", "T"});
    model.material("mat4").propertyGroup("def").func("rho")
         .set("expr", "pA*0.02801/8.314/T");
    model.material("mat4").propertyGroup("def").func("rho")
         .set("dermethod", "manual");
    model.material("mat4").propertyGroup("def").func("rho")
         .set("plotargs", new String[][]{{"pA", "0", "1"}, {"T", "0", "1"}});
    model.material("mat4").propertyGroup("def").func("rho")
         .set("argders", new String[][]{{"pA", "d(pA*0.02801/8.314/T,pA)"}, {"T", "d(pA*0.02801/8.314/T,T)"}});
    model.material("mat4").propertyGroup("def").func("k")
         .set("pieces", new String[][]{{"200.0", "1200.0", "3.6969697E-4+9.74353924E-5*T^1-4.07587413E-8*T^2+7.68453768E-12*T^3"}});
    model.material("mat4").propertyGroup("def").func("k").set("arg", "T");
    model.material("mat4").propertyGroup("def")
         .set("dynamicviscosity", "eta(T[1/K])[Pa*s]");
    model.material("mat4").propertyGroup("def")
         .set("ratioofspecificheat", "1.4");
    model.material("mat4").propertyGroup("def").set("density", "rho_N2");
    model.material("mat4").propertyGroup("def")
         .set("thermalconductivity", new String[]{"k(T[1/K])[W/(m*K)]", "0", "0", "0", "k(T[1/K])[W/(m*K)]", "0", "0", "0", "k(T[1/K])[W/(m*K)]"});
    model.material("mat4").propertyGroup("def").set("soundspeed", "c_N2");
    model.material("mat4").propertyGroup("def").addInput("temperature");
    model.material("mat4").propertyGroup("def").addInput("pressure");
    model.material("mat4").propertyGroup("idealGas").set("Rs", "297");
    model.material("mat4").propertyGroup("idealGas")
         .set("heatcapacity", "1040");
    model.material("mat4").propertyGroup("idealGas")
         .set("ratioofspecificheat", "1.4");
    model.material("mat4").propertyGroup("idealGas")
         .set("molarmass", "28.0e-3");
    model.material("mat4").propertyGroup("idealGas").addInput("temperature");
    model.material("mat4").propertyGroup("idealGas").addInput("pressure");
    model.material("mat1").name("Hydrogen");
    model.material("mat1").propertyGroup("def").func("eta")
         .set("pieces", new String[][]{{"200.0", "1300.0", "2.14524642E-6+2.54245E-8*T^1-1.0235587E-11*T^2+2.80895021E-15*T^3"}});
    model.material("mat1").propertyGroup("def").func("eta").set("arg", "T");
    model.material("mat1").propertyGroup("def").func("Cp")
         .set("pieces", new String[][]{{"200.0", "1300.0", "10808.501+21.5799904*T^1-0.0444720318*T^2+3.85401176E-5*T^3-1.14979447E-8*T^4"}});
    model.material("mat1").propertyGroup("def").func("Cp").set("arg", "T");
    model.material("mat1").propertyGroup("def").func("rho")
         .set("args", new String[]{"pA", "T"});
    model.material("mat1").propertyGroup("def").func("rho")
         .set("expr", "pA*0.002016/8.314/T");
    model.material("mat1").propertyGroup("def").func("rho")
         .set("dermethod", "manual");
    model.material("mat1").propertyGroup("def").func("rho")
         .set("plotargs", new String[][]{{"pA", "0", "1"}, {"T", "0", "1"}});
    model.material("mat1").propertyGroup("def").func("rho")
         .set("argders", new String[][]{{"pA", "d(pA*0.002016/8.314/T,pA)"}, {"T", "d(pA*0.002016/8.314/T,T)"}});
    model.material("mat1").propertyGroup("def").func("k")
         .set("pieces", new String[][]{{"200.0", "1300.0", "0.00517975922+6.72778E-4*T^1-3.0388973E-7*T^2+6.58874687E-11*T^3"}});
    model.material("mat1").propertyGroup("def").func("k").set("arg", "T");
    model.material("mat1").propertyGroup("def")
         .set("dynamicviscosity", "eta(T[1/K])[Pa*s]");
    model.material("mat1").propertyGroup("def")
         .set("ratioofspecificheat", "1.41");
    model.material("mat1").propertyGroup("def")
         .set("heatcapacity", "Cp(T[1/K])[J/(kg*K)]");
    model.material("mat1").propertyGroup("def").set("density", "4.56");
    model.material("mat1").propertyGroup("def")
         .set("thermalconductivity", new String[]{"k(T[1/K])[W/(m*K)]", "0", "0", "0", "k(T[1/K])[W/(m*K)]", "0", "0", "0", "k(T[1/K])[W/(m*K)]"});
    model.material("mat1").propertyGroup("def").set("soundspeed", "1300");
    model.material("mat1").propertyGroup("def").addInput("temperature");
    model.material("mat1").propertyGroup("def").addInput("pressure");
    model.material("mat2").name("Copper");
    model.material("mat2").propertyGroup("def")
         .set("relpermeability", new String[]{"1", "0", "0", "0", "1", "0", "0", "0", "1"});
    model.material("mat2").propertyGroup("def")
         .set("electricconductivity", new String[]{"5.998e7[S/m]", "0", "0", "0", "5.998e7[S/m]", "0", "0", "0", "5.998e7[S/m]"});
    model.material("mat2").propertyGroup("def")
         .set("thermalexpansioncoefficient", new String[]{"17e-6[1/K]", "0", "0", "0", "17e-6[1/K]", "0", "0", "0", "17e-6[1/K]"});
    model.material("mat2").propertyGroup("def")
         .set("heatcapacity", "385[J/(kg*K)]");
    model.material("mat2").propertyGroup("def")
         .set("relpermittivity", new String[]{"1", "0", "0", "0", "1", "0", "0", "0", "1"});
    model.material("mat2").propertyGroup("def")
         .set("density", "9700[kg/m^3]");
    model.material("mat2").propertyGroup("def")
         .set("thermalconductivity", new String[]{"400[W/(m*K)]", "0", "0", "0", "400[W/(m*K)]", "0", "0", "0", "400[W/(m*K)]"});
    model.material("mat2").propertyGroup("def").set("soundspeed", "4760");
    model.material("mat2").propertyGroup("Enu")
         .set("youngsmodulus", "110e9[Pa]");
    model.material("mat2").propertyGroup("Enu").set("poissonsratio", "0.35");
    model.material("mat2").propertyGroup("linzRes").set("rho0", "");
    model.material("mat2").propertyGroup("linzRes").set("alpha", "");
    model.material("mat2").propertyGroup("linzRes").set("Tref", "");
    model.material("mat2").propertyGroup("linzRes")
         .set("rho0", "1.72e-8[ohm*m]");
    model.material("mat2").propertyGroup("linzRes")
         .set("alpha", "0.0039[1/K]");
    model.material("mat2").propertyGroup("linzRes").set("Tref", "298[K]");
    model.material("mat2").propertyGroup("linzRes").addInput("temperature");
    model.material("mat2").propertyGroup("CpCs").set("cp", "");
    model.material("mat2").propertyGroup("CpCs").set("cs", "");
    model.material("mat2").propertyGroup("CpCs").set("cp", "c_p_Cu");
    model.material("mat2").propertyGroup("CpCs").set("cs", "c_s_Cu");
    model.material("mat3").name("316 Stainless Steel");
    model.material("mat3").propertyGroup("def").set("density", "7.99e3");
    model.material("mat3").propertyGroup("def").set("soundspeed", "5790");
    model.material("mat3").propertyGroup("def")
         .set("relpermeability", new String[]{"1", "0", "0", "0", "1", "0", "0", "0", "1"});
    model.material("mat3").propertyGroup("def")
         .set("electricconductivity", new String[]{"1.42e6", "0", "0", "0", "1.42e6", "0", "0", "0", "1.42e6"});
    model.material("mat3").propertyGroup("def")
         .set("thermalexpansioncoefficient", new String[]{"1.6e-5", "0", "0", "0", "1.6e-5", "0", "0", "0", "1.6e-5"});
    model.material("mat3").propertyGroup("def").set("heatcapacity", "500");
    model.material("mat3").propertyGroup("def")
         .set("relpermittivity", new String[]{"1", "0", "0", "0", "1", "0", "0", "0", "1"});
    model.material("mat3").propertyGroup("def")
         .set("thermalconductivity", new String[]{"16.2", "0", "0", "0", "16.2", "0", "0", "0", "16.2"});
    model.material("mat3").propertyGroup("def").set("youngsmodulus", "194");
    model.material("mat3").propertyGroup("def").set("poissonsratio", "0.30");
    model.material("mat3").propertyGroup("def").set("lossfactor", ".04");
    model.material("mat3").propertyGroup("CpCs").set("cp", "");
    model.material("mat3").propertyGroup("CpCs").set("cs", "");
    model.material("mat3").propertyGroup("CpCs").set("cp", "c_p_SS");
    model.material("mat3").propertyGroup("CpCs").set("cs", "c_s_SS");
    model.material("mat5").name("Air");
    model.material("mat5").propertyGroup("def").func("eta")
         .set("pieces", new String[][]{{"200.0", "1600.0", "-8.38278E-7+8.35717342E-8*T^1-7.69429583E-11*T^2+4.6437266E-14*T^3-1.06585607E-17*T^4"}});
    model.material("mat5").propertyGroup("def").func("eta").set("arg", "T");
    model.material("mat5").propertyGroup("def").func("Cp")
         .set("pieces", new String[][]{{"200.0", "1600.0", "1047.63657-0.372589265*T^1+9.45304214E-4*T^2-6.02409443E-7*T^3+1.2858961E-10*T^4"}});
    model.material("mat5").propertyGroup("def").func("Cp").set("arg", "T");
    model.material("mat5").propertyGroup("def").func("rho")
         .set("args", new String[]{"pA", "T"});
    model.material("mat5").propertyGroup("def").func("rho")
         .set("expr", "pA*0.02897/8.314/T");
    model.material("mat5").propertyGroup("def").func("rho")
         .set("dermethod", "manual");
    model.material("mat5").propertyGroup("def").func("rho")
         .set("plotargs", new String[][]{{"pA", "0", "1"}, {"T", "0", "1"}});
    model.material("mat5").propertyGroup("def").func("rho")
         .set("argders", new String[][]{{"pA", "d(pA*0.02897/8.314/T,pA)"}, {"T", "d(pA*0.02897/8.314/T,T)"}});
    model.material("mat5").propertyGroup("def").func("k")
         .set("pieces", new String[][]{{"200.0", "1600.0", "-0.00227583562+1.15480022E-4*T^1-7.90252856E-8*T^2+4.11702505E-11*T^3-7.43864331E-15*T^4"}});
    model.material("mat5").propertyGroup("def").func("k").set("arg", "T");
    model.material("mat5").propertyGroup("def").func("cs")
         .set("args", new String[]{"T"});
    model.material("mat5").propertyGroup("def").func("cs")
         .set("expr", "sqrt(1.4*287*T)");
    model.material("mat5").propertyGroup("def").func("cs")
         .set("dermethod", "manual");
    model.material("mat5").propertyGroup("def").func("cs")
         .set("plotargs", new String[][]{{"T", "0", "1"}});
    model.material("mat5").propertyGroup("def").func("cs")
         .set("argders", new String[][]{{"T", "d(sqrt(1.4*287*T),T)"}});
    model.material("mat5").propertyGroup("def")
         .set("dynamicviscosity", "eta(T[1/K])[Pa*s]");
    model.material("mat5").propertyGroup("def")
         .set("ratioofspecificheat", "1.4");
    model.material("mat5").propertyGroup("def")
         .set("electricconductivity", new String[]{"0[S/m]", "0", "0", "0", "0[S/m]", "0", "0", "0", "0[S/m]"});
    model.material("mat5").propertyGroup("def")
         .set("density", "rho(pA[1/Pa],T[1/K])[kg/m^3]");
    model.material("mat5").propertyGroup("def")
         .set("thermalconductivity", new String[]{"k(T[1/K])[W/(m*K)]", "0", "0", "0", "k(T[1/K])[W/(m*K)]", "0", "0", "0", "k(T[1/K])[W/(m*K)]"});
    model.material("mat5").propertyGroup("def")
         .set("soundspeed", "cs(T[1/K])[m/s]");
    model.material("mat5").propertyGroup("def").addInput("temperature");
    model.material("mat5").propertyGroup("def").addInput("pressure");
    model.material("mat5").propertyGroup("idealGas").set("Rs", "286.9");
    model.material("mat5").propertyGroup("idealGas")
         .set("heatcapacity", "1010");
    model.material("mat5").propertyGroup("idealGas")
         .set("ratioofspecificheat", "1.4");
    model.material("mat5").propertyGroup("idealGas")
         .set("molarmass", "28.97e-3");
    model.material("mat5").propertyGroup("idealGas").addInput("temperature");
    model.material("mat5").propertyGroup("idealGas").addInput("pressure");

    model.physics("actd").feature("aas1").set("f0", "2e6[Hz]");
    model.physics("actd").feature("aas1").set("AperLength", "1e3");
    model.physics("actd").feature("aas1").set("tp", "1.0[us]");
    model.physics("actd").feature("aas1").set("Type", "GaussianPulse");
    model.physics("astd").feature("tpam1").set("FluidModel", "IdealGas");
    model.physics("astd").feature("tpam1").set("editModelInputs", "1");
    model.physics("astd").feature("tpam1").set("minput_pressure", "1 [atm]");
    model.physics("astd").feature("dlemm1").set("IsotropicOption", "CpCs");
    model.physics("astd").feature("dlemm1")
         .name("Linear Elastic Material (SS)");
    model.physics("astd").feature("dlemm2").set("IsotropicOption", "CpCs");
    model.physics("astd").feature("dlemm2")
         .name("Linear Elastic Material (Cu)");
    model.physics("astd").feature("fix1").active(false);
    model.physics("astd").feature("init2")
         .set("u", new String[][]{{"0"}, {"0"}, {"10e-6"}});
    model.physics("astd").feature("init2").active(false);
    model.physics("astd").feature("init2")
         .name("Upstream Initial Displacement");
    model.physics("astd").feature("init3")
         .set("u", new String[][]{{"0"}, {"0"}, {"-10e-6"}});
    model.physics("astd").feature("init3").active(false);
    model.physics("astd").feature("init3")
         .name("Downstream Initial Displacement");
    model.physics("astd").feature("gpsa1").set("tp", "t0");
    model.physics("astd").feature("gpsa1").set("AArea", "-A_r");
    model.physics("astd").feature("gpsa1").set("f0", "f0");
    model.physics("astd").feature("bndl1")
         .set("FperArea", new String[][]{{"0"}, {"0"}, {"z/abs(z)*1.5e6*exp(-(t-1e-6[s])^2*2e12[s^-2]) [N/m^2]"}});
    model.physics("astd").feature("bndl1").set("LoadType", "TotalForce");
    model.physics("astd").feature("bndl1")
         .set("Ftot", new String[][]{{"0"}, {"0"}, {"z/abs(z)*A_z*exp(-f0^2*(t-t0)^2/2)"}});
    model.physics("astd").feature("bndl1").active(false);
    model.physics("astd").feature("tel1").set("SpringType", "kTot");
    model.physics("astd").feature("tel1")
         .set("kTot", new String[][]{{"k_tot_bf_r"}, {"0"}, {"k_tot_bf_z"}});
    model.physics("astd").feature("tel1").name("Button/Flange Contact Layer");
    model.physics("astd").feature("tel2").set("SpringType", "kTot");
    model.physics("astd").feature("tel2")
         .set("kTot", new String[][]{{"k_tot_ff_r"}, {"0"}, {"k_tot_ff_z"}});
    model.physics("astd").feature("tel2").name("Flange/Flange Contact Layer");

    model.mesh("mesh1").feature("size").set("hauto", 3);
    model.mesh("mesh1").run();

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

    model.batch().create("p1", "Parametric");
    model.batch("p1").feature().create("so1", "Solutionseq");
    model.batch("p1").study("std1");

    model.result().dataset().create("cpt1", "CutPoint2D");
    model.result().dataset().create("cpt2", "CutPoint2D");
    model.result().dataset().create("cpt3", "CutPoint2D");
    model.result().dataset().create("cpt4", "CutPoint2D");
    model.result().dataset().create("rev1", "Revolve2D");
    model.result().dataset().create("rev2", "Revolve2D");
    model.result().dataset().create("gp1_ds1", "Function1D");
    model.result().dataset("cpt1").set("data", "dset2");
    model.result().dataset("cpt3").set("data", "dset2");
    model.result().dataset("cpt4").set("data", "dset2");
    model.result().dataset("rev1").set("data", "dset2");
    model.result().dataset("rev2").set("data", "dset2");
    model.result().dataset().remove("dset3");
    model.result().dataset().remove("dset4");
    model.result().dataset().remove("dset5");
    model.result().dataset().remove("dset6");
    model.result().dataset().remove("dset7");
    model.result().dataset().remove("dset8");
    model.result().create("pg1", "PlotGroup1D");
    model.result().create("pg3", "PlotGroup1D");
    model.result().create("pg2", "PlotGroup2D");
    model.result().create("pg4", "PlotGroup2D");
    model.result().create("pg5", "PlotGroup3D");
    model.result().create("pg6", "PlotGroup2D");
    model.result().create("pg7", "PlotGroup2D");
    model.result().create("pg8", "PlotGroup3D");
    model.result().create("pg9", "PlotGroup3D");
    model.result().create("pg10", "PlotGroup1D");
    model.result("pg1").feature().create("ptgr1", "PointGraph");
    model.result("pg1").feature().create("ptgr2", "PointGraph");
    model.result("pg3").feature().create("ptgr8", "PointGraph");
    model.result("pg3").feature().create("ptgr3", "PointGraph");
    model.result("pg3").feature().create("ptgr7", "PointGraph");
    model.result("pg3").feature().create("ptgr4", "PointGraph");
    model.result("pg3").feature().create("ptgr5", "PointGraph");
    model.result("pg3").feature().create("ptgr6", "PointGraph");
    model.result("pg3").feature("ptgr7").selection().set(new int[]{54});
    model.result("pg2").feature().create("con1", "Contour");
    model.result("pg2").feature().create("con2", "Contour");
    model.result("pg2").feature().create("con3", "Contour");
    model.result("pg2").feature().create("arws1", "ArrowSurface");
    model.result("pg2").feature().create("surf2", "Surface");
    model.result("pg2").feature().create("surf1", "Surface");
    model.result("pg4").feature().create("surf1", "Surface");
    model.result("pg5").feature().create("surf1", "Surface");
    model.result("pg6").feature().create("surf1", "Surface");
    model.result("pg6").feature("surf1").feature().create("def", "Deform");
    model.result("pg7").feature().create("surf1", "Surface");
    model.result("pg7").feature("surf1").feature().create("hght", "Height");
    model.result("pg8").feature().create("surf1", "Surface");
    model.result("pg8").feature("surf1").feature().create("def", "Deform");
    model.result("pg9").feature().create("surf1", "Surface");
    model.result("pg10").feature().create("plot1", "LineGraph");
    model.result().export().create("plot1", "Plot");

    model.study("std1").feature("param")
         .set("pname", new String[]{"k_tot_ff_r", "k_tot_ff_z", "k_tot_bf_z"});
    model.study("std1").feature("param")
         .set("plistarr", new String[]{"1e10", "1e10", "1e8"});
    model.study("std1").feature("param").set("sweeptype", "filled");
    model.study("std1").feature("time")
         .set("tlist", "range(0,2[us],1000[us])");
    model.study("std1").feature("time")
         .set("activate", new String[]{"actd", "off", "astd", "on"});

    model.sol("sol1").attach("std1");
    model.sol("sol1").feature("st1")
         .name("Compile Equations: Time Dependent");
    model.sol("sol1").feature("st1").set("studystep", "time");
    model.sol("sol1").feature("v1").set("control", "time");
    model.sol("sol1").feature("v1").feature("comp1_p").set("solvefor", false);
    model.sol("sol1").feature("t1")
         .set("atol", new String[]{"comp1_p2", "1e-3", "comp1_u", "1e-3", "comp1_p", "1e-3"});
    model.sol("sol1").feature("t1").set("tlist", "range(0,2[us],1000[us])");
    model.sol("sol1").feature("t1").set("timemethod", "genalpha");
    model.sol("sol1").feature("t1")
         .set("atoludotactive", new String[]{"comp1_p2", "off", "comp1_u", "off", "comp1_p", "off"});
    model.sol("sol1").feature("t1")
         .set("atoludot", new String[]{"comp1_p2", "1e-3", "comp1_u", "1e-3", "comp1_p", "1e-3"});
    model.sol("sol1").feature("t1")
         .set("atolmethod", new String[]{"comp1_p2", "global", "comp1_u", "global", "comp1_p", "global"});
    model.sol("sol1").feature("t1").set("timestepgenalpha", "2e-6");
    model.sol("sol1").feature("t1").set("tstepsgenalpha", "manual");
    model.sol("sol1").feature("t1").set("control", "time");
    model.sol("sol1").feature("t1").set("rhoinf", "0");
    model.sol("sol2").name("Parametric 2");

    model.batch("p1").set("control", "param");
    model.batch("p1").set("err", true);
    model.batch("p1").set("sweeptype", "filled");
    model.batch("p1").set("plistarr", new String[]{"1e10", "1e10", "1e8"});
    model.batch("p1")
         .set("pname", new String[]{"k_tot_ff_r", "k_tot_ff_z", "k_tot_bf_z"});
    model.batch("p1").set("control", "param");
    model.batch("p1").feature("so1").set("psol", "sol2");
    model.batch("p1").feature("so1")
         .set("param", new String[]{"\"k_tot_ff_r\",\"1e10\",\"k_tot_ff_z\",\"1e10\",\"k_tot_bf_z\",\"4e7\"", "\"k_tot_ff_r\",\"1e10\",\"k_tot_ff_z\",\"1e10\",\"k_tot_bf_z\",\"6e7\"", "\"k_tot_ff_r\",\"1e10\",\"k_tot_ff_z\",\"1e10\",\"k_tot_bf_z\",\"8e7\"", "\"k_tot_ff_r\",\"1e10\",\"k_tot_ff_z\",\"1e10\",\"k_tot_bf_z\",\"1.2e8\"", "\"k_tot_ff_r\",\"1e10\",\"k_tot_ff_z\",\"1e10\",\"k_tot_bf_z\",\"1.4e8\"", "\"k_tot_ff_r\",\"1e10\",\"k_tot_ff_z\",\"1e10\",\"k_tot_bf_z\",\"1.6e8\""});
    model.batch("p1").feature("so1").set("seq", "sol1");
    model.batch("p1").attach("std1");
    model.batch("p1").run();

    model.result().dataset("cpt1").name("Inner Radius");
    model.result().dataset("cpt1").set("bndsnap", true);
    model.result().dataset("cpt1").set("pointx", "InnerRadius");
    model.result().dataset("cpt1").set("pointy", "0");
    model.result().dataset("cpt2").name("Inner Wall");
    model.result().dataset("cpt2").set("bndsnap", true);
    model.result().dataset("cpt2").set("pointx", "ButtonRadius+1[mm]");
    model.result().dataset("cpt2").set("pointy", "-InnerHeight/2");
    model.result().dataset("cpt3").name("S1");
    model.result().dataset("cpt3").set("bndsnap", true);
    model.result().dataset("cpt3").set("pointx", "15.24");
    model.result().dataset("cpt3").set("pointy", "0");
    model.result().dataset("cpt4").name("S0");
    model.result().dataset("cpt4").set("bndsnap", true);
    model.result().dataset("cpt4").set("pointx", "0.5*InnerRadius");
    model.result().dataset("cpt4").set("pointy", "-InnerHeight/2-EndHeight");
    model.result().dataset("rev2").set("startangle", "-90");
    model.result().dataset("rev2").set("hasspacevars", "on");
    model.result().dataset("rev2").set("revangle", "225");
    model.result().dataset("gp1_ds1").set("par1", "t");
    model.result().dataset("gp1_ds1").set("parmax1", "2.4999999999999998E-6");
    model.result().dataset("gp1_ds1").set("function", "gp1");
    model.result().dataset("gp1_ds1").set("parmin1", "-5.000000000000001E-7");
    model.result("pg1").name("1D Gas Plots");
    model.result("pg1").set("ylabel", "Total acoustic pressure field (Pa)");
    model.result("pg1").set("manualgrid", "on");
    model.result("pg1").set("xlabel", "Time (s)");
    model.result("pg1").set("xspacing", "2.0E-6");
    model.result("pg1").set("yspacing", "1000000");
    model.result("pg1").set("xlabelactive", false);
    model.result("pg1").set("ylabelactive", false);
    model.result("pg1").feature("ptgr1").set("data", "cpt1");
    model.result("pg1").feature("ptgr1").set("expr", "astd.p_t");
    model.result("pg1").feature("ptgr2").active(false);
    model.result("pg1").feature("ptgr2").set("data", "cpt2");
    model.result("pg3").name("1D Solid Plots");
    model.result("pg3").set("data", "dset2");
    model.result("pg3")
         .set("ylabel", "Acceleration, Z component (m/s<sup>2</sup>)");
    model.result("pg3").set("manualgrid", "on");
    model.result("pg3").set("xlabel", "Time (s)");
    model.result("pg3").set("xspacing", "2.0E-6");
    model.result("pg3").set("yspacing", "1.0E-12");
    model.result("pg3").set("xlabelactive", false);
    model.result("pg3").set("ylabelactive", false);
    model.result("pg3").feature("ptgr8").name("S0 Z Acceleration");
    model.result("pg3").feature("ptgr8").set("data", "cpt4");
    model.result("pg3").feature("ptgr8").set("expr", "astd.u_ttZ");
    model.result("pg3").feature("ptgr8").set("unit", "m/s^2");
    model.result("pg3").feature("ptgr8")
         .set("descr", "Acceleration, Z component");
    model.result("pg3").feature("ptgr3").active(false);
    model.result("pg3").feature("ptgr3").name("S1 Displacement");
    model.result("pg3").feature("ptgr3").set("data", "cpt3");
    model.result("pg3").feature("ptgr3").set("expr", "-u");
    model.result("pg3").feature("ptgr3").set("unit", "m");
    model.result("pg3").feature("ptgr3").set("descr", "-u");
    model.result("pg3").feature("ptgr7").active(false);
    model.result("pg3").feature("ptgr7").name("S1 Displacement 1");
    model.result("pg3").feature("ptgr7").set("data", "dset2");
    model.result("pg3").feature("ptgr7").set("expr", "-u");
    model.result("pg3").feature("ptgr7").set("unit", "m");
    model.result("pg3").feature("ptgr7").set("descr", "-u");
    model.result("pg3").feature("ptgr4").active(false);
    model.result("pg3").feature("ptgr4").name("S0 Displacement");
    model.result("pg3").feature("ptgr4").set("data", "cpt4");
    model.result("pg3").feature("ptgr4").set("expr", "w");
    model.result("pg3").feature("ptgr4").set("unit", "m");
    model.result("pg3").feature("ptgr4")
         .set("descr", "Displacement field, Z component");
    model.result("pg3").feature("ptgr5").active(false);
    model.result("pg3").feature("ptgr5").name("S1 Pressure");
    model.result("pg3").feature("ptgr5").set("data", "cpt3");
    model.result("pg3").feature("ptgr5").set("expr", "astd.pm");
    model.result("pg3").feature("ptgr5").set("unit", "N/m^2");
    model.result("pg3").feature("ptgr5").set("descr", "Pressure");
    model.result("pg3").feature("ptgr6").active(false);
    model.result("pg3").feature("ptgr6").name("S0 Pressure");
    model.result("pg3").feature("ptgr6").set("data", "cpt4");
    model.result("pg3").feature("ptgr6").set("expr", "astd.pm");
    model.result("pg3").feature("ptgr6").set("unit", "N/m^2");
    model.result("pg3").feature("ptgr6").set("descr", "Pressure");
    model.result("pg2").set("data", "dset2");
    model.result("pg2").feature("con1").active(false);
    model.result("pg2").feature("con1").name("Pressure Contours");
    model.result("pg2").feature("con1").set("expr", "astd.p_t");
    model.result("pg2").feature("con1").set("levelmethod", "levels");
    model.result("pg2").feature("con1")
         .set("levels", "range(-90000000,180000000/99,90000000)");
    model.result("pg2").feature("con2").active(false);
    model.result("pg2").feature("con2").name("R Displacement Contours");
    model.result("pg2").feature("con2").set("expr", "-u");
    model.result("pg2").feature("con2").set("levelmethod", "levels");
    model.result("pg2").feature("con2")
         .set("levels", "range(-8.6e-5,8.6e-5/99,8.6e-5)");
    model.result("pg2").feature("con2").set("unit", "m");
    model.result("pg2").feature("con2").set("descr", "-u");
    model.result("pg2").feature("con3").active(false);
    model.result("pg2").feature("con3").name("Z Displacement Contours");
    model.result("pg2").feature("con3").set("expr", "w");
    model.result("pg2").feature("con3").set("levelmethod", "levels");
    model.result("pg2").feature("con3")
         .set("levels", "range(-8.6e-5,8.6e-5/99,8.6e-5)");
    model.result("pg2").feature("con3").set("unit", "m");
    model.result("pg2").feature("con3")
         .set("descr", "Displacement field, Z component");
    model.result("pg2").feature("arws1").name("Solid Displacement Field");
    model.result("pg2").feature("arws1").set("scaleactive", true);
    model.result("pg2").feature("arws1").set("scale", "2542575.03892885");
    model.result("pg2").feature("arws1").set("xnumber", "50");
    model.result("pg2").feature("arws1").set("ynumber", "50");
    model.result("pg2").feature("arws1").set("arrowlength", "logarithmic");
    model.result("pg2").feature("surf2").name("Solid Displacement Magnitude");
    model.result("pg2").feature("surf2").set("descr", "Total displacement");
    model.result("pg2").feature("surf2").set("rangecolormax", "1.6E-9");
    model.result("pg2").feature("surf2").set("expr", "astd.disp");
    model.result("pg2").feature("surf2").set("rangecoloractive", "on");
    model.result("pg2").feature("surf2").set("rangecolormin", "1.0E-9");
    model.result("pg2").feature("surf2").set("unit", "m");
    model.result("pg2").feature("surf1").name("Gas Pressure");
    model.result("pg2").feature("surf1").set("rangecolormax", "600");
    model.result("pg2").feature("surf1").set("expr", "astd.p_t");
    model.result("pg2").feature("surf1").set("rangecoloractive", "on");
    model.result("pg2").feature("surf1").set("rangecolormin", "-1000");
    model.result("pg4").name("Acoustic Pressure (actd)");
    model.result("pg4").set("data", "dset2");
    model.result("pg5").name("Acoustic Pressure, 3D (actd)");
    model.result("pg6").name("Displacement (astd)");
    model.result("pg6").set("data", "dset2");
    model.result("pg6").feature("surf1").set("descr", "Total displacement");
    model.result("pg6").feature("surf1").set("expr", "astd.disp");
    model.result("pg6").feature("surf1").set("unit", "m");
    model.result("pg7").name("Acoustic Pressure (astd)");
    model.result("pg7").set("data", "dset2");
    model.result("pg7").feature("surf1").set("expr", "astd.p_t");
    model.result("pg7").feature("surf1").set("colortable", "WaveLight");
    model.result("pg7").feature("surf1").set("colortablesym", true);
    model.result("pg8").name("Displacement, 3D (astd)");
    model.result("pg8").set("data", "rev2");
    model.result("pg8").feature("surf1").set("expr", "astd.disp");
    model.result("pg8").feature("surf1").set("unit", "m");
    model.result("pg8").feature("surf1").set("descr", "Total displacement");
    model.result("pg8").feature("surf1").feature("def")
         .set("descr", "Displacement field (Material)");
    model.result("pg8").feature("surf1").feature("def")
         .set("descractive", true);
    model.result("pg8").feature("surf1").feature("def")
         .set("expr", new String[]{"cos(rev2phi)*u", "sin(rev2phi)*u", "w"});
    model.result("pg9").name("Acoustic Pressure, 3D (astd)");
    model.result("pg9").set("data", "rev2");
    model.result("pg9").feature("surf1").set("colortablesym", true);
    model.result("pg9").feature("surf1").set("expr", "astd.p_t");
    model.result("pg9").feature("surf1").set("colortable", "WaveLight");
    model.result("pg10").set("data", "none");
    model.result("pg10").set("xlabelactive", true);
    model.result("pg10").set("title", "gp1");
    model.result("pg10").set("titletype", "manual");
    model.result("pg10").set("ylabelactive", true);
    model.result("pg10").feature("plot1").set("data", "gp1_ds1");
    model.result("pg10").feature("plot1").set("solrepresentation", "solnum");
    model.result("pg10").feature("plot1").set("unit", "");
    model.result("pg10").feature("plot1").set("xdataunit", "");
    model.result("pg10").feature("plot1").set("xdata", "expr");
    model.result("pg10").feature("plot1").set("expr", "gp1(root.t)");
    model.result("pg10").feature("plot1").set("xdataexpr", "root.t");
    model.result("pg10").feature("plot1").set("descr", "gp1");
    model.result("pg10").feature("plot1").set("xdatadescr", "Time");
    model.result("pg10").feature("plot1").set("xdataunit", "");
    model.result().export("plot1")
         .set("filename", "C:\\Users\\plane\\Desktop\\Data\\COMSOL\\HPRF\\Spark with Thin Elastic Layer\\S0_1ms_ktot_ff_3.8e10.csv");
    model.result().export("plot1").set("plotgroup", "pg3");
    model.result().export("plot1").set("plot", "ptgr8");

    return model;
  }

}
