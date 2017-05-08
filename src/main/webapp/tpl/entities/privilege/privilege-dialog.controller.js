(function() {
    'use strict';

    angular
        .module('app')
        .controller('PrivilegeDialogController', PrivilegeDialogController);

    PrivilegeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal','DataUtils', 'entity', 'Privilege'];

    function PrivilegeDialogController ($timeout, $scope, $stateParams, $uibModalInstance,$uibModal, DataUtils, entity, Privilege ) {
        var vm = this;

        vm.privilege = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        
      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.privilege.id !== null) {
                Privilege.update(vm.privilege, onSaveSuccess, onSaveError);
            } else {
                Privilege.save(vm.privilege, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:privilegeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        
        
         function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        
         vm.setMimage = function ($file, fieldName) {
                if ($file && $file.$error === 'pattern') {
                    return;
                }
                if ($file) {
                    DataUtils.toBase64($file, function (base64Data) {
                        $scope.$apply(function () {
                            vm.privilege[fieldName] = base64Data;
                            vm.privilege[fieldName + 'ContentType'] = $file.type;
                        });
                    });
                }
            };
            
            vm.zoomColumn = function (lookupCtrl,lookupTemplate, fieldname, entity) {
                $uibModal.open({
                    templateUrl: 'tpl/entities/'+lookupTemplate+'/'+lookupTemplate+'-dialog.html',
                    controller: lookupCtrl+'DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return entity;
                        }
                    }
                }).result.then(function(item) {
                        vm.privilege[fieldname] = item;
                }, function() {
                    
                });
            };

    }
})();
