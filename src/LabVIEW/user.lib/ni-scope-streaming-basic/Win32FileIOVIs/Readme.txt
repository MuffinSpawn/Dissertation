
Win32 File I/O VIs
==================

The LabVIEW VIs in this directory provide access to non-buffered file I/O in Windows.  This is useful for sequential reads and writes of large amounts of data at high data rates.  Non-buffered file I/O disables the Windows file cache, so data is written directly to or read directly from the file I/O driver for the hard disk drive or RAID controller.  

The read VIs in this directory take an incoming array as input; the data read from disk is placed in the input array provided.  This leads to much higher performance, since the array can be preallocated ahead of time.  The Read Binary File VI in LabVIEW performs at much lower rates 

The write VIs in this directory are there only for completeness; you should be able to use the Win32 Open File VI to open the file in non-buffered mode and use the LabVIEW Write Binary File VI thereafter.

These VIs are compiled in LabVIEW 8.0, although they should work in earlier versions of LabVIEW if saved for that version. 

Note that the Win32 Open File VI outputs a LabVIEW file refnum as well as a Windows file handle.  This allows you to call LabVIEW file I/O VIs such as Get File Size or Set File Position along with the Win32 Read File VIs.

Benchmark VIs that help you test the maximum read and write speeds for your hard drive or RAID configuration are located in the FileIOBenchmarkVIs directory at the same level as this Win32FileIOVIs directory.


Restrictions/Requirements
=========================

Data must be written or read in quantities that are a multiple of the sector size (512 bytes).

Data must be aligned in memory on boundaries required by the IDE, SATA, or RAID controller.  The memory alignment requirement is typically 2 bytes, although nVIDIA onboard RAID controllers require 4 bytes and Adaptec RAID controllers require 8 byte alignment.  Most data arrays allocated by modern compilers are 8-byte aligned, so this is usually not a problem.
