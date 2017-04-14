package com.wetrack.ikongtiao.admin.controllers;

/**
 * Created by zhanghong on 15/12/28.
 */
//@Controller
//@ResponseBody
//@RequestMapping(value = "/machine")
public class MachineController {


//    @Value("${store.dir}")
//    String storePath;
//
//    @Value("${store.dir.image.machine}")
//    String urlPath;
//
//    @Autowired
//    MachineFieldDefRepo machineFieldDefRepo;
//
//    @Autowired
//    MachineTypeFieldRepo machineTypeFieldRepo;
//
//    @Autowired
//    PlateTemplateRepo plateTemplateRepo;
//
//    @Autowired
//    MachineTypeJpaRepo machineTypeRepo;
//
//    @Autowired
//    MachineUnitRepo machineUnitRepo;
//
//    @Autowired
//    MachineUnitValueRepo machineUnitValueRepo;
//
//    @Autowired
//    MachineService machineService;
//
//    @RequestMapping(value = "/fieldDef/list" , method = {RequestMethod.GET})
//    public List<MachineFieldDef> listFiledDef(@RequestParam(value = "nameLike", required = false) String nameLike) {
//        if(StringUtils.isEmpty(nameLike)){
//            return machineFieldDefRepo.findAll();
//        }
//        return machineFieldDefRepo.findByNameLike(nameLike);
//    }
//
//
//    @RequestMapping(value = "/fieldDef/create", method = RequestMethod.POST)
//    public void addFieldDef(@RequestBody MachineFieldDef machineFieldDef){
//        if(StringUtils.isEmpty(machineFieldDef.getName())){
//            throw new BusinessException("", "没有名称");
//        }
//        if(machineFieldDefRepo.countByName(machineFieldDef.getName()) > 0){
//            throw new BusinessException("", "名称已经存在，不能重复");
//        }
//        machineFieldDefRepo.save(machineFieldDef);
//    }
//
//    @RequestMapping(value = "/plateTemplate/list" , method = {RequestMethod.GET})
//    public List<PlateTemplate> listPlateTemplate(){
//        return plateTemplateRepo.findAll();
//    }
//
//    @RequestMapping(value = "/plateTemplate/save", method = RequestMethod.POST)
//    public void addPlateTemplate(@RequestBody PlateTemplate plateTemplate){
//        if(StringUtils.isEmpty(plateTemplate.getTemplateHtml())){
//            throw new BusinessException("", "模版为空");
//        }
//
//        plateTemplateRepo.save(plateTemplate);
//    }
//
//
//    @Transactional(rollbackFor = Exception.class)
//    @RequestMapping(value = "/machineType/save", method = RequestMethod.POST)
//    public void addMachineType(@RequestBody MachineTypeForm machineType){
//        //update or save
//
//        if(machineType.getId() == null) {
//            if(StringUtils.isEmpty(machineType.getName())){
//                throw new BusinessException("", "型号不能为空");
//            }else if(machineTypeRepo.countByName(machineType.getName()) > 0){
//                throw new BusinessException("", "该型号已经存在");
//            }
//            MachineType saved = machineTypeRepo.save(machineType);
//            machineType.typeFields.forEach(field -> field.setTypeId(saved.getId()));
//            machineTypeFieldRepo.save(machineType.typeFields);
//        }else{
//            //先删除所有的字段再添加？
//            machineTypeFieldRepo.deleteByTypeId(machineType.getId());
////            machineTypeRepo.save(machineType);
//            machineTypeFieldRepo.save(machineType.typeFields);
//        }
//    }
//
//    @Transactional(rollbackFor = Exception.class)
//    @RequestMapping(value = "/machineUnit/create", method = RequestMethod.POST)
//    public Long addMachineUnit(@RequestBody MachineUnitDto machineUnitDto){
//        MachineUnit basic = new MachineUnit();
//        BeanUtils.copyProperties(machineUnitDto, basic);
//        if(StringUtils.isEmpty(basic.getBuildNo())){
//            throw new BusinessException("", "制造编号没有填写");
//        }else if(machineUnitRepo.countByBuildNo(basic.getBuildNo()) > 0){
//            throw new BusinessException("", "该制造编号的机组已经存在");
//        }
//
//        //生成机组二维码
//        String fileName = "pl_" + basic.getBuildNo() + ".png";
//        String qrCodeImagePath = urlPath + "/" + fileName;
//        File f = new File(storePath + urlPath, fileName);
//        f.setLastModified(System.currentTimeMillis());
//        try {
//            FileOutputStream stream = new FileOutputStream(f);
//            QRCode.from(basic.getBuildNo())
//                    .withSize(300, 300)
//                    .to(ImageType.PNG)
//                    .writeTo(stream);
//            stream.close();
//            basic.setQrCodeImg(qrCodeImagePath);
//        } catch (Exception e) {
//            throw new BusinessException("", "二维码文件保存失败, " + e.getMessage());
//        }
//
//
//        MachineUnit saved = machineUnitRepo.save(basic);
//
//        if(machineUnitDto.getValues() != null && machineUnitDto.getValues().size() > 0) {
//            List<MachineUnitValue> vs = machineUnitDto.getValues().stream()
//                    .map(value ->{
//                        MachineUnitValue v = new MachineUnitValue();
//                        BeanUtils.copyProperties(value, v);
//                        v.setUnitId(saved.getId());
//                        return v;
//                    })
//                    .collect(Collectors.toList());
//
//
//            machineUnitValueRepo.save(vs);
//        }
//
//        return saved.getId();
//    }
//
//    @RequestMapping(value = "/machineUnit/{id}", method = RequestMethod.GET)
//    public MachineUnitDto getMachineUnit(@PathVariable(value = "id") long id){
//        return machineUnitRepo.findDtoById(id);
//    }
//
//    @RequestMapping(value = "/machineUnit/list", method = RequestMethod.POST)
//    public PageList<MachineUnitDto> listMachineUnits(@RequestBody MachineUnitQueryParam param){
//        return machineService.searchMachineUnits(param);
//    }
//
//    @Data
//    public static class MachineTypeForm extends MachineType{
//
//        List<MachineTypeField> typeFields;
//    }

}
