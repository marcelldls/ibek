cd "$(TOP)"
epicsEnvSet "EPICS_CA_MAX_ARRAY_BYTES", '6000000'
epicsEnvSet "EPICS_TS_MIN_WEST", '0'
cd "$(TOP)"
dbLoadDatabase "dbd/ioc.dbd"
ioc_registerRecordDeviceDriver(pdbbase)
pmacAsynIPConfigure(BRICK1port, 172.23.240.97:1025)
pmacCreateController(BRICK1, BRICK1port, 0, 8, 100, 200)
pmacCreateAxes(BRICK1, 8)

dbLoadRecords("pmacController.template", "PORT=BRICK1port, P=BRICK1, TIMEOUT=200, FEEDRATE=150, CSG0=, CSG1=, CSG2=, CSG3=, CSG4=, CSG5=, CSG6=, CSG7=, ")
dbLoadRecords("pmacStatus.template", "PORT=BRICK1port, P=BRICK1, Description=, ControlIP=, ControlPort=, ControlMode=")
dbLoadRecords("$(PMAC)/db/dls_pmac_asyn_motor.template", "P=MOTORBRICK1,M=:motor1,PORT=BRICK1,ADDR=1,DESC=motor1,MRES=0.0001,VELO=20,PREC=,EGU=mm,TWV=1,DTYP=pmac.DlsPmacAsynMotor,DIR=0,VBAS=0,VMAX=20,ACCL=0.5,BDST=0,BVEL=0,BACC=,DHLM=10000,DLLM=,HLM=None,LLM=None,HLSV=MAJOR,INIT=,SREV=1000,RRES=None,ERES=None,JAR=None,UEIP=0,RDBL=,RLINK=,RTRY=0,DLY=0,OFF=0,RDBD=None,FOFF=0,ADEL=0,NTM=1,FEHIGH=0,FEHIHI=0,FEHHSV=NO_ALARM,FEHSV=NO_ALARM,SCALE=1,HOMEVIS=1,HOMEVISSTR="Use motor summary screen",name="",alh=None,gda_name="",gda_desc="",SPORT="",HOME="",PMAC=,ALLOW_HOMED_SET=""")
dbLoadRecords("$(PMAC)/db/dls_pmac_asyn_motor.template", "P=MOTORBRICK1,M=:motor2,PORT=BRICK1,ADDR=2,DESC=motor2,MRES=0.0001,VELO=20,PREC=,EGU=mm,TWV=1,DTYP=pmac.DlsPmacAsynMotor,DIR=0,VBAS=0,VMAX=20,ACCL=0.5,BDST=0,BVEL=0,BACC=,DHLM=10000,DLLM=,HLM=None,LLM=None,HLSV=MAJOR,INIT=,SREV=1000,RRES=None,ERES=None,JAR=None,UEIP=0,RDBL=,RLINK=,RTRY=0,DLY=0,OFF=0,RDBD=None,FOFF=0,ADEL=0,NTM=1,FEHIGH=0,FEHIHI=0,FEHHSV=NO_ALARM,FEHSV=NO_ALARM,SCALE=1,HOMEVIS=1,HOMEVISSTR="Use motor summary screen",name="",alh=None,gda_name="",gda_desc="",SPORT="",HOME="",PMAC=,ALLOW_HOMED_SET=""")
dbLoadRecords("$(PMAC)/db/dls_pmac_asyn_motor.template", "P=MOTORBRICK1,M=:motor3,PORT=BRICK1,ADDR=3,DESC=motor3,MRES=0.0001,VELO=20,PREC=,EGU=mm,TWV=1,DTYP=pmac.DlsPmacAsynMotor,DIR=0,VBAS=0,VMAX=20,ACCL=0.5,BDST=0,BVEL=0,BACC=,DHLM=10000,DLLM=,HLM=None,LLM=None,HLSV=MAJOR,INIT=,SREV=1000,RRES=None,ERES=None,JAR=None,UEIP=0,RDBL=,RLINK=,RTRY=0,DLY=0,OFF=0,RDBD=None,FOFF=0,ADEL=0,NTM=1,FEHIGH=0,FEHIHI=0,FEHHSV=NO_ALARM,FEHSV=NO_ALARM,SCALE=1,HOMEVIS=1,HOMEVISSTR="Use motor summary screen",name="",alh=None,gda_name="",gda_desc="",SPORT="",HOME="",PMAC=,ALLOW_HOMED_SET=""")
dbLoadRecords("$(PMAC)/db/dls_pmac_asyn_motor.template", "P=MOTORBRICK1,M=:motor4,PORT=BRICK1,ADDR=4,DESC=motor4,MRES=0.0001,VELO=20,PREC=,EGU=mm,TWV=1,DTYP=pmac.DlsPmacAsynMotor,DIR=0,VBAS=0,VMAX=20,ACCL=0.5,BDST=0,BVEL=0,BACC=,DHLM=10000,DLLM=,HLM=None,LLM=None,HLSV=MAJOR,INIT=,SREV=1000,RRES=None,ERES=None,JAR=None,UEIP=0,RDBL=,RLINK=,RTRY=0,DLY=0,OFF=0,RDBD=None,FOFF=0,ADEL=0,NTM=1,FEHIGH=0,FEHIHI=0,FEHHSV=NO_ALARM,FEHSV=NO_ALARM,SCALE=1,HOMEVIS=1,HOMEVISSTR="Use motor summary screen",name="",alh=None,gda_name="",gda_desc="",SPORT="",HOME="",PMAC=,ALLOW_HOMED_SET=""")
dbLoadRecords("$(PMAC)/db/dls_pmac_asyn_motor.template", "P=MOTORBRICK1,M=:motor5,PORT=BRICK1,ADDR=5,DESC=motor5,MRES=0.0001,VELO=20,PREC=,EGU=mm,TWV=1,DTYP=pmac.DlsPmacAsynMotor,DIR=0,VBAS=0,VMAX=20,ACCL=0.5,BDST=0,BVEL=0,BACC=,DHLM=10000,DLLM=,HLM=None,LLM=None,HLSV=MAJOR,INIT=,SREV=1000,RRES=None,ERES=None,JAR=None,UEIP=0,RDBL=,RLINK=,RTRY=0,DLY=0,OFF=0,RDBD=None,FOFF=0,ADEL=0,NTM=1,FEHIGH=0,FEHIHI=0,FEHHSV=NO_ALARM,FEHSV=NO_ALARM,SCALE=1,HOMEVIS=1,HOMEVISSTR="Use motor summary screen",name="",alh=None,gda_name="",gda_desc="",SPORT="",HOME="",PMAC=,ALLOW_HOMED_SET=""")
dbLoadRecords("$(PMAC)/db/dls_pmac_asyn_motor.template", "P=MOTORBRICK1,M=:motor6,PORT=BRICK1,ADDR=6,DESC=motor6,MRES=0.0001,VELO=20,PREC=,EGU=mm,TWV=1,DTYP=pmac.DlsPmacAsynMotor,DIR=0,VBAS=0,VMAX=20,ACCL=0.5,BDST=0,BVEL=0,BACC=,DHLM=10000,DLLM=,HLM=None,LLM=None,HLSV=MAJOR,INIT=,SREV=1000,RRES=None,ERES=None,JAR=None,UEIP=0,RDBL=,RLINK=,RTRY=0,DLY=0,OFF=0,RDBD=None,FOFF=0,ADEL=0,NTM=1,FEHIGH=0,FEHIHI=0,FEHHSV=NO_ALARM,FEHSV=NO_ALARM,SCALE=1,HOMEVIS=1,HOMEVISSTR="Use motor summary screen",name="",alh=None,gda_name="",gda_desc="",SPORT="",HOME="",PMAC=,ALLOW_HOMED_SET=""")
dbLoadRecords("$(PMAC)/db/dls_pmac_asyn_motor.template", "P=MOTORBRICK1,M=:motor7,PORT=BRICK1,ADDR=7,DESC=motor7,MRES=0.0001,VELO=20,PREC=,EGU=mm,TWV=1,DTYP=pmac.DlsPmacAsynMotor,DIR=0,VBAS=0,VMAX=20,ACCL=0.5,BDST=0,BVEL=0,BACC=,DHLM=10000,DLLM=,HLM=None,LLM=None,HLSV=MAJOR,INIT=,SREV=1000,RRES=None,ERES=None,JAR=None,UEIP=0,RDBL=,RLINK=,RTRY=0,DLY=0,OFF=0,RDBD=None,FOFF=0,ADEL=0,NTM=1,FEHIGH=0,FEHIHI=0,FEHHSV=NO_ALARM,FEHSV=NO_ALARM,SCALE=1,HOMEVIS=1,HOMEVISSTR="Use motor summary screen",name="",alh=None,gda_name="",gda_desc="",SPORT="",HOME="",PMAC=,ALLOW_HOMED_SET=""")
dbLoadRecords("$(PMAC)/db/dls_pmac_asyn_motor.template", "P=MOTORBRICK1,M=:motor8,PORT=BRICK1,ADDR=8,DESC=motor8,MRES=0.0001,VELO=20,PREC=,EGU=mm,TWV=1,DTYP=pmac.DlsPmacAsynMotor,DIR=0,VBAS=0,VMAX=20,ACCL=0.5,BDST=0,BVEL=0,BACC=,DHLM=10000,DLLM=,HLM=None,LLM=None,HLSV=MAJOR,INIT=,SREV=1000,RRES=None,ERES=None,JAR=None,UEIP=0,RDBL=,RLINK=,RTRY=0,DLY=0,OFF=0,RDBD=None,FOFF=0,ADEL=0,NTM=1,FEHIGH=0,FEHIHI=0,FEHHSV=NO_ALARM,FEHSV=NO_ALARM,SCALE=1,HOMEVIS=1,HOMEVISSTR="Use motor summary screen",name="",alh=None,gda_name="",gda_desc="",SPORT="",HOME="",PMAC=,ALLOW_HOMED_SET=""")

cd "$(TOP)"
iocInit