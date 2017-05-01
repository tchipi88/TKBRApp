(function() {
    'use strict';

    angular
        .module('app')
        .controller('EmployeDepartementDialogController', EmployeDepartementDialogController);

    EmployeDepartementDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal','DataUtils', 'entity', 'EmployeDepartement'];

    function EmployeDepartementDialogController ($timeout, $scope, $stateParams, $uibModalInstance,$uibModal, DataUtils, entity, EmployeDepartement ) {
        var vm = this;

        vm.employeDepartement = entity;
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
            if (vm.employeDepartement.id !== null) {
                EmployeDepartement.update(vm.employeDepartement, onSaveSuccess, onSaveError);
            } else {
                EmployeDepartement.save(vm.employeDepartement, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:employeDepartementUpdate', result);
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
                            vm.employeDepartement[fieldName] = base64Data;
                            vm.employeDepartement[fieldName + 'ContentType'] = $file.type;
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
                        vm.employeDepartement[fieldname] = item;
                }, function() {
                    
                });
            };

    }
})();
