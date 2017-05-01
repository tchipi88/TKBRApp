(function() {
    'use strict';

    angular
        .module('app')
        .controller('TerrainReglementDialogController', TerrainReglementDialogController);

    TerrainReglementDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal','DataUtils', 'entity', 'TerrainReglement','TerrainCommande'];

    function TerrainReglementDialogController ($timeout, $scope, $stateParams, $uibModalInstance,$uibModal, DataUtils, entity, TerrainReglement ,TerrainCommande) {
        var vm = this;

        vm.terrainReglement = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
       // vm.commandes = TerrainCommande.query();

      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.terrainReglement.id !== null) {
                TerrainReglement.update(vm.terrainReglement, onSaveSuccess, onSaveError);
            } else {
                TerrainReglement.save(vm.terrainReglement, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:terrainReglementUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


         vm.datePickerOpenStatus.dateVersement = false;

        
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
                            vm.terrainReglement[fieldName] = base64Data;
                            vm.terrainReglement[fieldName + 'ContentType'] = $file.type;
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
                        vm.terrainReglement[fieldname] = item;
                }, function() {
                    
                });
            };

    }
})();
